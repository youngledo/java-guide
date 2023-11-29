# JDK Flight Recorder

>
注意⚠️：Java Flight
Recorder需要商业许可证才能在生产中使用。要了解有关商业功能以及如何启用这些功能的更多信息，请访问[http://www.oracle.com/technetwork/java/javaseproducts/](http://www.oracle.com/technetwork/java/javaseproducts/)。
>
> 参考文献：https://docs.oracle.com/javacomponents/jmc-5-5/jfr-runtime-guide/about.htm#JFRRT107

# 一、关于Java Flight Recorder

Java Flight Recorder (JFR) 是一种用于收集、诊断和分析正在运行的Java应用程序数据的工具。它集成到java虚拟机 (JVM)
中，几乎不会导致性能开销，因此即使在负载较重的生产环境中也可以使用。使用默认设置时，性能影响小于1%。对于一些应用，它可以显著更低。但是，对于短时间运行的应用程序 (
不是在生产环境中运行的应用程序)，相对启动和预热时间可能会更长，这可能会对性能产生1% 以上的影响。JFR收集有关JVM以及在其上运行的Java应用程序的数据。

与其他类似工具相比，JFR具有以下优点：

- **提供更好的数据**: JFR从运行时的各个部分捕获数据，并且已经做出了巨大的努力来确保捕获的数据代表系统的真实状态。这种努力的示例包括最小化观察者效应，并且能够捕获安全点之外的样本。
- **提供更好的数据模型**: 数据模型是自描述的。无论大小，记录都包含理解数据所需的一切。
- **提供更好的性能**： Flight Recorder引擎本身经过了性能优化。我们已注意确保数据捕获不会撤消优化或以其他方式对性能产生负面影响。有些数据实际上可以免费获得，因为运行时已经捕获了这些数据。
- **允许第三方事件提供程序**：一组API使JFR能够从第三方应用程序(包括WebLogic服务器和其他Oracle产品)捕获数据。
- **降低总体拥有成本**：JFR使您能够花更少的时间诊断和排除问题，降低运营成本和业务中断，在出现问题时提供更快的解决时间，并提高系统效率。

JFR主要用于：

- **分析**：JFR不断地获取有关正在运行的系统的信息。此评测信息包括执行评测（显示程序在哪里花费时间）、线程停滞/延迟评测（显示线程未运行的原因）、分配评测（显示分配压力在哪里）、垃圾收集详细信息等。
- **黑盒分析**：JFR不断地将信息保存到循环缓冲区中。由于开销很低，飞行记录仪可以一直打开。以后在查找特定异常的原因时，可以访问这些信息。
- **支持和调试**：在联系Oracle支持以帮助诊断Java应用程序的问题时，JFR收集的数据非常重要。

## 1.1 了解事件

Java Flight
Recorder收集有关事件的数据。事件发生在JVM或Java应用程序中的特定时间点。每个事件都有一个名称、一个时间戳和一个可选负载。有效负载是与事件相关联的数据，例如CPU使用率、事件前后的Java堆大小、锁持有者的线程ID等。

大多数事件还包含有关事件发生的线程、事件发生时的堆栈跟踪以及事件持续时间的信息。使用事件中可用的信息，可以重建JVM和Java应用程序的运行时详细信息。

JFR收集有关四种类型事件的信息：

- 即时事件立即发生，并立即记录。
- 持续时间事件有一个开始时间和一个结束时间，并在完成时记录。
- 定时事件是具有可选的用户定义阈值的持续时间事件，因此仅记录持续时间超过指定时间段的事件。这对于其他类型的事件是不可能的。

JFR以极高的详细程度监视正在运行的系统。这产生了大量的数据。为了保持尽可能低的开销，将记录的事件的类型限制为您实际需要的事件。在大多数情况下，非常短的持续时间的事件是不感兴趣的，所以限制记录事件的持续时间超过一定的有意义的阈值。

## 1.2 了解数据流

JFR从JVM（通过内部API）和Java应用程序(通过JFR API)
收集数据。此数据存储在较小的线程本地缓冲区中，这些缓冲区将刷新到全局内存中缓冲区。然后将全局内存缓冲区中的数据写入磁盘。磁盘写入操作的开销很大，因此您应该仔细选择要记录的事件数据，尽量将其降至最低。二进制记录文件的格式非常紧凑和高效，便于应用程序读写。

各种缓冲区之间没有信息重叠。特定的数据块可以在内存或磁盘上使用，但不能同时在这两个位置使用。这有以下影响：

- 发生电源故障时，尚未刷新到磁盘缓冲区的数据将不可用。
- JVM崩溃可能导致一些数据在核心文件（即内存缓冲区）中可用，而一些数据在磁盘缓冲区中可用。JFR不提供合并此类缓冲区的能力。
- 在JFR收集的数据可供您使用之前，可能会有一个小延迟（例如，在使其可见之前，必须将其移动到不同的缓冲区时）。
- 记录文件中的数据可能不是按时间顺序排列的，因为数据是从几个线程缓冲区按块收集的。

在某些情况下，JVM会丢弃事件顺序，以确保它不会崩溃。任何无法以足够快的速度写入磁盘的数据都将被丢弃。当这种情况发生时，录制文件将包含受影响时间段的信息。这些信息也将被记录到JVM的日志记录工具中。

您可以将JFR配置为不向磁盘写入任何数据。在这种模式下，全局缓冲区充当循环缓冲区，当缓冲区已满时，最旧的数据将被丢弃。这种开销非常低的操作模式仍然可以收集根本原因问题分析所需的所有重要数据。因为最新的数据总是在全局缓冲区中可用，所以只要操作或监视系统检测到问题，就可以按需将其写入磁盘。但是，在此模式下，只有最后几分钟的数据可用，因此它只包含最近的事件。如果您需要获得长时间的完整操作历史记录，请使用默认模式，即定期将事件写入磁盘。

## 1.3 Java Flight Recorder架构

JFR由以下组件组成：

- JFR运行时是JVM内部产生记录的记录引擎。运行时引擎本身由以下组件组成:
    - 代理控制缓冲区、磁盘I/O、MBean等。该组件提供了一个用C和Java代码编写的动态库，还提供了一种独立于JVM的纯Java实现。
    - 生产者将数据插入缓冲区。他们可以从JVM和Java应用程序以及 (通过Java API) 从第三方应用程序收集事件。
- Java Mission Control（JMC）的Flight Recorder插件使您能够从JMC客户端使用JFR，使用图形用户界面（GUI）启动、停止和配置记录，以及查看记录文件。

## 1.4 启用Java Flight Recorder

默认情况下，JFR在JVM中处于禁用状态。要启用JFR，必须使用-XX:+FlightRecorder选项启动Java应用程序。由于JFR是一项商业功能，仅在基于Java平台标准版（Oracle
Java SE Advanced和Oracle Java SE Suite）的商业软件包中提供，因此您还必须使用-XX:+UnlockCommercialFeatures选项启用商业功能。

例如，要在启动名为`MyApp`的Java应用程序时启用JFR，请使用以下命令：

```bash
java -XX:+UnlockCommercialFeatures -XX:+FlightRecorder MyApp
```

或者，（如果使用JDK8u40或更高版本）您可以在运行时从JMC本身启用JFR。当您启动新的飞行记录时，将出现一个对话框，说明：

```
Commercial Features are not enabled in the JVM. To start a Flight Recording, you need to enable Commercial Features. Do you want to do that now?
```

点击“是”启用这些功能。

您还可以使用适当的`jcmd`诊断命令在运行的JVM中启用Java Flight
Recorder。有关示例，请参阅[2.2节“使用诊断命令”](#22-使用诊断命令)。

请注意，当在JVM上运行依赖lambda形式的替代语言时，比如JavaScript实现Nashorn，堆栈跟踪的深度可能会非常深。为了确保对具有大堆栈的堆栈轨迹进行正确采样，您可能需要增加
Flight Recorder堆栈深度。将其值设置为1024通常就足够了：

```bash
java -XX:+UnlockCommercialFeatures -XX:+FlightRecorder -XX:FlightRecorderOptions=stackdepth=1024 MyApp
```

### 1.4.1 提高JFR方法分析器的保真度

JFR方法分析器的一个很好的特性是，它不需要线程处于安全点就可以对堆栈进行采样。然而，由于常见的情况是仅在安全点遍历堆栈，因此HotSpot通常不提供代码的非安全点部分的元数据，这意味着这些样本将不会被正确地解析为正确的行号和BCI。也就是说，除非您指定：

```
-XX:+UnlockDiagnosticVMOptions -XX:+DebugNonSafepoints
```

使用`DebugNonSafePoints`，编译器将为不在安全点的代码部分生成必要的元数据。

# 二、运行Java Flight Recorder

您可以同时运行多个录制，并使用不同的设置配置每个录制；特别是，您可以配置不同的录制来捕获不同的事件集。但是，为了使Java Flight
Recorder的内部逻辑尽可能精简，生成的记录始终包含当时所有活动记录的所有事件的并集。这意味着，如果有多个录制正在运行，那么录制中的信息可能会比您想要的更多。这可能令人困惑，但没有其他负面影响。

使用JFR最简单、最直观的方法是通过集成到Java任务控制中的 Flight
Recorder插件。该插件允许通过直观的GUI访问JFR功能。有关使用JMC客户端控制JFR的更多信息，请参阅Java任务控制帮助的 Flight
Recorder插件部分。

本章介绍了运行和管理JFR记录的更高级方法，并包含以下部分：

- [使用命令行](#21-使用命令行)
- [使用诊断命令](#22-使用诊断命令)
- [配置录制](#23-配置录制)
- [自动创建录制](#24-自动创建录制)
- [安全](#25-安全)
- [故障排除](#26-故障排除)

## 2.1 使用命令行

在创建飞行记录之前，您必须先解锁商业功能并启用Java Flight Recorder。这可以通过多种方式完成，从`java`
命令行选项到jcmd诊断命令，再到Java任务控制中的图形用户界面 (GUI) 控件。这种灵活性使您能够在启动时提供适当的选项，或者在JVM已经运行之后与JFR进行交互。

以下示例使用`java`命令行选项运行MyApp并立即开始60秒的录制。录音将保存到一个名为myrecoding.jfr的文件中：

```bash
java -XX:+UnlockCommercialFeatures -XX:+FlightRecorder -XX:StartFlightRecording=duration=60s,filename=myrecording.jfr MyApp
```

有关支持的命令行选项的更多信息，请参阅[附录A的“命令行选项”](#a1-命令行选项)。

## 2.2 使用诊断命令

可以使用`jcmd`和JFR特定的诊断命令来控制记录。有关诊断命令的详细说明，请参阅[附录A的“诊断命令参考”](#a2-诊断命令参考)。

执行诊断命令的最简单方法是使用`jcmd`
工具（位于Java安装目录中）。要发出命令，必须将JVM的进程标识符（或主类的名称）和实际命令作为参数传递给`jcmd`。

例如，要使用标识符5368在正在运行的Java进程上启动一个60秒的录制，并将其保存到当前目录中的myrecoding.jfr中，请使用以下命令：

```bash
jcmd 5368 JFR.start duration=60s filename=myrecording.jfr
```

要查看所有正在运行的Java进程的列表，请运行不带任何参数的`jcmd`
命令。要查看运行中的Java应用程序可用的命令的完整列表，请在进程标识符（或主类的名称）后面指定`help`作为诊断命令。

下面的示例展示了使用Java Flight Recorder运行`jcmd`的各种用例，假定MyApp.jar中有一个演示程序。

##### 示例2-1 使用jcmd的动态交互

示例2-1解锁了商业功能，并在运行时动态启用Java Flight Recorder。启动Java应用程序时不提供任何额外选项。JVM运行后，`jcmd`
命令`VM.unlock_commercial_features`和`JFR.start`将用于解锁商业功能并开始新的飞行记录。

```bash
$java -jar MyApp.jar
$jcmd 40019 VM.command_line
40019:
VM Arguments:
java_command: MyApp.jar
java_class_path (initial): MyApp.jar
Launcher Type: SUN_STANDARD

$jcmd 40019 VM.check_commercial_features
40019:
Commercial Features are locked.

$jcmd 40019 JFR.check
40019:
Java Flight Recorder not enabled.

Use VM.unlock_commercial_features to enable.

$jcmd 40019 VM.unlock_commercial_features
40019:
Commercial Features now unlocked.

$jcmd 40019 VM.check_commercial_features
40019:
Commercial Features are unlocked.
Status of individual features:
  Java Flight Recorder has not been used.
Resource Management is disabled.

$jcmd 40019 JFR.check
40019:

No available recordings.

Use JFR.start to start a recording.

$jcmd 40019 JFR.start name=my_recording filename=myrecording.jfr dumponexit=true
40019:
Started recording 1. No limit (duration/maxsize/maxage) in use.

Use JFR.dump name=my_recording to copy recording data to file.

$jcmd 40019 VM.check_commercial_features
40019:
Commercial Features are unlocked.
Status of individual features:
  Java Flight Recorder has been used.
  Resource Management is disabled.

$jcmd 40019 JFR.check
40019:
Recording: recording=1 name="my_recording" filename="myrecording.jfr" compress=false (running)
```

##### 示例2-2使用-XX:+UnlockCommercialFeatures和-XX:+FlightRecorder

示例2-2通过在应用程序启动时将选项`-XX:+UnlockCommercialFeatures`和`-XX:+FlightRecorder`传递到`java`命令来解锁商业功能并启用JFR。

```bash
$java -XX:+UnlockCommercialFeatures -XX:+FlightRecorder -jar MyApp.jar

$jcmd 37152 VM.command_line
37152:
VM Arguments:
jvm_args: -XX:+UnlockCommercialFeatures -XX:+FlightRecorder
java_command: MyApp.jar
java_class_path (initial): MyApp.jar
Launcher Type: SUN_STANDARD
$jcmd 37152 VM.check_commercial_features
37152:
Commercial Features are unlocked.
Status of individual features:
  Java Flight Recorder has not been used.
  Resource Management is disabled.

$jcmd 37152 JFR.check
37152:
No available recordings.

Use JFR.start to start a recording.

$jcmd 37152 JFR.start name=my_recording filename=myrecording.jfr dumponexit=true
37152:
Started recording 1. No limit (duration/maxsize/maxage) in use.

Use JFR.dump name=my_recording to copy recording data to file.


$jcmd 37152 JFR.check
37152:
Recording: recording=1 name="my_recording" filename="myrecording.jfr" compress=false (running)

$jcmd 37152 VM.check_commercial_features
37152:
Commercial Features are unlocked.
Status of individual features:
  Java Flight Recorder has been used.
  Resource Management is disabled.
```

##### 示例2-3 使用带有JFR动态启动的-XX: UnlockCommercialFeatures

示例2-3在使用`-XX:UnlockCommercialFeatures`应用程序启动后动态启动JFR（使用`JFR.start`）。

```bash
$java -XX:+UnlockCommercialFeatures -jar MyApp.jar

$jcmd 39970 VM.command_line
39970:
VM Arguments:
jvm_args: -XX:+UnlockCommercialFeatures
java_command: MyApp.jar
java_class_path (initial): MyApp.jar
Launcher Type: SUN_STANDARD

$jcmd 39970 VM.check_commercial_features
39970:
Commercial Features are unlocked.
Status of individual features:
  Java Flight Recorder has not been used.
  Resource Management is disabled.

$jcmd 39970 JFR.check
39970:
No available recordings.

Use JFR.start to start a recording.

$jcmd 39970 VM.check_commercial_features
39970:
Commercial Features are unlocked.
Status of individual features:
  Java Flight Recorder has not been used.
  Resource Management is disabled.

$jcmd 39970 JFR.start name=my_recording filename=myrecording.jfr dumponexit=true

39970:

Started recording 1. No limit (duration/maxsize/maxage) in use.

Use JFR.dump name=my_recording to copy recording data to file.

$jcmd 39970 VM.check_commercial_features

39970:

Commercial Features are unlocked.
Status of individual features:
  Java Flight Recorder has been used.
  Resource Management is disabled.

$jcmd 39970 JFR.check
39970:
Recording: recording=1 name="my_recording" filename="myrecording.jfr" compress=false (running)
```

##### 示例2-4 使用-XX:-UnlockCommercialFeatures锁定商业功能

启动具有明确锁定的商业功能的应用程序（`-XX:-UnlockCommercialFeatures`）。然后，它使用`VM.unlock_commercial_features`
解锁商业功能，并使用`JFR.start`开始新的飞行记录。

```bash
$ java -XX:-UnlockCommercialFeatures -jar MyApp.jar

$jcmd 40110 VM.command_line
40110:
VM Arguments:
jvm_args: -XX:-UnlockCommercialFeatures
java_command: MyApp.jar
java_class_path (initial): MyApp.jar
Launcher Type: SUN_STANDARD

$jcmd 40110 VM.check_commercial_features
40110:
Commercial Features are locked.

$jcmd 40110 VM.unlock_commercial_features
40110:
Commercial Features now unlocked.

$jcmd 40110 VM.check_commercial_features
40110:
Commercial Features are unlocked.
Status of individual features:
  Java Flight Recorder has not been used.
  Resource Management is disabled.

$jcmd 40110 JFR.start name=my_recording filename=myrecording.jfr dumponexit=true
40110:
Started recording 1. No limit (duration/maxsize/maxage) in use.

Use JFR.dump name=my_recording to copy recording data to file.

$jcmd 40110 JFR.check
40110:
Recording: recording=1 name="my_recording" filename="myrecording.jfr" compress=false (running)

$jcmd 40110 VM.check_commercial_features
40110:
Commercial Features are unlocked.
Status of individual features:
  Java Flight Recorder has been used.
  Resource Management is disabled.
```

##### 示例2-5 使用-XX:FlightRecorder禁用JFR

示例2-5通过在应用程序启动时将`-XX:-FlightRecorder`传递到`java`命令来完全禁用JFR。如果已指定此选项，则无法动态创建新的飞行记录。

```bash
$java -XX:+UnlockCommercialFeatures -XX:-FlightRecorder -jar MyApp.jar
$jcmd 39589 VM.command_line
39589:
VM Arguments:
jvm_args: -XX:+UnlockCommercialFeatures -XX:-FlightRecorder 
java_command: MyApp.jar
java_class_path (initial): MyApp.jar
Launcher Type: SUN_STANDARD

$jcmd 39589 VM.check_commercial_features

39589:
Commercial Features are unlocked.
Status of individual features:
  Java Flight Recorder is disabled.
  Resource Management is disabled.

$jcmd 39589 JFR.check
39589:
Java Flight Recorder is disabled.

$jcmd 39589 JFR.stop
39589:
Java Flight Recorder is disabled.

$jcmd 39589 VM.unlock_commercial_features
39589:
Commercial Features already unlocked.

$jcmd 39589 JFR.start name=my_recording filename=myrecording.jfr dumponexit=true
39589:
Java Flight Recorder is disabled.
```

##### 示例2-6 无效的选项组合

示例2-6显示了当无效的选项组合被传递给`java`命令时会发生什么。在这种情况下，用户尝试启用JFR（`-XX:+FlightRecorder`
），同时锁定商业功能（`-XX:UnlockCommercialFeatures`）。

```bash
$ java -XX:-UnlockCommercialFeatures -XX:+FlightRecorder -jar MyApp.jar
Error: To use 'FlightRecorder', first unlock using -XX:+UnlockCommercialFeatures.
Error: Could not create the Java Virtual Machine.
Error: A fatal exception has occurred. Program will exit.
```

## 2.3 配置录制

您可以通过多种其他方式配置显式录制。无论您以何种方式开始录制(即，使用命令行方法还是使用诊断命令)，这些技术都是相同的。

### 2.3.1 设置最大大小和年龄

您可以使用以下参数将显式录制配置为具有最大大小或年龄：

- `maxsize=size`：追加字母`k`或`K`表示千字节，`m`或`M`表示兆字节，`g`或`G`表示千兆字节，或者不指定任何后缀来设置字节大小。
- `maxage=age`：追加字母`s`表示秒，`m`表示分钟，`h`表示小时，或`d`表示天。

如果同时指定了大小限制和年龄，则在达到任一限制时都会删除数据。￼

### 2.3.2 设置延迟

在计划录制时。您可能希望在实际开始录制之前添加延迟；例如，当从命令行运行时，您可能希望应用程序启动或在开始录制之前达到稳定状态。为此，请使用`delay`
参数：

```
delay=delay
```

追加字母`s`表示秒，`m`表示分钟，`h`表示小时，或`d`表示天。

### 2.3.3 设置压缩

尽管录制文件的格式非常紧凑，但您可以通过将其添加到ZIP存档中来进一步压缩它。要启用压缩，请使用以下参数：

```
compress=true
```

请注意，压缩需要CPU资源，这可能会对性能产生负面影响。￼

## 2.4 自动创建录制

当使用默认记录运行时，您可以配置Java Flight Recorder，以便在出现某些情况时自动将当前内存中的记录数据保存到文件中。如果使用磁盘存储库，则还将包括磁盘存储库中的当前信息。￼

### 2.4.1 在退出时创建录制

要在JVM每次退出时将录制数据保存到指定路径，请使用以下选项启动应用程序：

```
-XX:FlightRecorderOptions=defaultrecording=true,dumponexit=true,dumponexitpath=path
```

设置记录保存位置的路径。如果指定一个目录，则会在该目录中创建一个以日期和时间为名称的文件。如果指定文件名，则使用该名称。如果未指定路径，则录制将保存在当前目录中。

您还可以将`dumponexit=true`指定为`-XX:StartFlightRecording`选项的参数：

```
-XX:StartFlightRecording=name=test,filename=D:\test.jfr,dumponexit=true
```

在这种情况下，转储文件（dump）将被写入filename参数定义的位置。

### 2.4.2 使用触发器创建记录

您可以使用Java Mission Control中的控制台来设置触发器。触发器是只要规则指定的条件为真（true）就执行操作的规则。例如，您可以创建一条规则，在堆大小超过100
MB时触发飞行记录开始。Java Mission Control中的触发器可以使用通过JMX MBean公开的任何属性作为规则的输入。他们可以启动许多其他行动，而不仅仅是Flight
Recorder转储。

在JMX控制台的`Triggers`选项卡上定义触发器。有关如何创建触发器的更多信息，请参阅Java Mission Control帮助。

## 2.5 安全

记录文件可能包含机密信息，例如Java命令行选项和环境变量。存储或传输记录文件时要格外小心，就像处理诊断核心文件或堆转储一样。

表2-1描述了使用JFR的各种方法的安全权限。

#### 表2-1安全权限

| 方式                      | 安全                                     |                             
|:------------------------|:---------------------------------------|
| 命令行                     | 任何能够访问Java进程的命令行的人都必须是可信的。             |
| 诊断命令                    | 只有Java进程的所有者才能使用jcmd来控制该进程。            |
| Java Mission Control客户端 | Java Mission Control Client使用JMX访问JVM。 |

## 2.6 故障排除

通过使用以下选项之一启动JVM，您可以从Java Flight Recorder收集大量诊断信息：

- `-XX:FlightRecorderOptions=loglevel=debug`
- `-XX:FlightRecorderOptions=loglevel=trace`

# 附录A 命令参考

本附录是可用于Java Flight Recorder的命令的基本参考。它包含以下部分：

- 命令行选项
- 诊断命令参考

## A.1 命令行选项

使用`java`命令启动Java应用程序时，可以指定启用Java Flight Recorder的选项、配置其设置并开始录制。以下命令行选项特定于Java
Flight Recorder：

- `-XX:+|-FlightRecorder`
- `-XX:FlightRecorderOptions`
- `-XX:StartFlightRecording`

> 注意⚠️：
> 只有当您对系统有充分的了解时，才应该使用-XX选项。如果不正确地使用这些命令，可能会影响系统的稳定性或性能-XX选项是实验性的，随时可能发生变化。

## A.2 诊断命令参考

这是可用于控制Java Flight Recorder的诊断命令以及每个命令可用的参数的说明。通过运行`jcmd`
命令并指定进程标识符，然后是help参数和命令名称，也可以获得此信息。例如，要在标识符为5361的正在运行的JVM进程上获取`JFR.start`
命令的帮助信息，请运行以下命令：

```
jcmd 5361 help JFR.start
```

要获得JVM可用的诊断命令的完整列表，请不要指定命令的名称。

与Java Flight Recorder相关的诊断命令包括：

- `JFR.start`
- `JFR.check`
- `JFR.stop`
- `JFR.dump`
- `VM.unlock_commercial_features`
- `VM.check_commercial_features`

### A.2.1 JFR.start

`JFR.start`诊断命令启动飞行记录。表A-1列出了可用于此命令的参数。

表A-1 JFR.start

| Parameter        | Description   | Type of value | Default                   |
|------------------|---------------|---------------|---------------------------|
| name             | 录制名称          | String        |                           |
| settings         | 服务器端模板        | String        |                           |
| defaultrecording | 启动默认录制        | Boolean       | False                     |
| delay            | 延迟开始录制        | Time          | 0s                        |
| duration         | 录制持续时间        | Time          | 0s (means "forever")      |
| filename         | 生成的录制文件名      | String        |                           |
| compress         | GZip压缩生成的录制文件 | Boolean       | False                     |
| maxage           | 缓冲区数据的最大年龄    | Time          | 0s (means "no age limit") |
| maxsize          | 缓冲区的最大大小（字节）  | Long          | 0 (means "no max size")   |

### A.2.2 JFR.check

`JFR.check`命令显示有关正在运行的录制的信息。表A-2列出了可用于此命令的参数。

表A-2 JFR.check

| Parameter | Description | Type of value | Default |
|-----------|-------------|---------------|---------|
| name      | 录制名称        | String        |         |
| recording | 录制ID        | Long          | 1       |
| verbose   | 打印详细数据      | Boolean       | False   |

### A.2.3 JFR.stop

`JFR.stop`诊断命令停止运行飞行记录。表A-3列出了可用于此命令的参数。

表A-3 JFR.stop

| Parameter     | Description            | Type of value | Default |
|---------------|------------------------|---------------|---------|
| name          | 录制名称                   | String        |         |
| recording     | 录制ID                   | Long          | 1       |
| discard       | 丢弃录制数据                 | Boolean       |         |
| copy_to_file  | 将录制数据复制到文件             | String        |         |
| compress_copy | GZip压缩“copy_to_file”目标 | Boolean       | False   |

### A.2.4 JFR.dump

`JFR.dump`诊断命令停止运行飞行记录。表A-4列出了可用于此命令的参数。

表A-4 JFR.dump

| Parameter     | Description            | Type of value | Default |
|---------------|------------------------|---------------|---------|
| name          | 录制名称                   | String        |         |
| recording     | 录制ID                   | Long          | 1       |
| copy_to_file  | 将录制数据复制到文件             | String        |         |
| compress_copy | GZip压缩“copy_to_file”目标 | Boolean       | False   |

### A.2.5 VM.unlock_commercial_features

`VM.unlock_commercial_features`命令用于解锁已经运行的JVM中的商业功能。此命令没有参数。

### A.2.6 VM.check_commercial_features

`VM.check_commercial_features`命令用于检查商业功能在已经运行的JVM中是锁定还是解锁。此命令没有参数。