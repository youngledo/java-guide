# java
> java命令启动Java应用程序。它通过启动Java虚拟机 (JVM)、加载指定的类并调用该类的 main()方法来完成此操作。 
> 
> 参考文档：https://docs.oracle.com/en/java/javase/11/tools/java.html#GUID-3B1CE181-CD30-4178-9602-230B800D4FAE

## 概要
Windows操作系统：javaw命令与java完全相同，只是javaw没有关联的控制台窗口。如果不希望出现命令提示窗口，请使用javaw。不过，如果启动失败，javaw 启动器会显示一个包含错误信息的对话框。

- 启动一个类文件：
  ```bash
  java [options] mainclass [args ...]
  ```
- 启动jar文件的主类：执行封装在JAR文件中的程序。jarfile参数是JAR文件的名称，其清单包含*Main-Class:classname*形式的行，该行使用`public static void main(String[] args)`方法定义类，该方法用作应用程序的起点。使用 -jar 时，指定的 JAR 文件是所有用户类的源，其他类路径设置将被忽略。
  ```bash
  java [options] -jar jarfile [args ...]
  ```
- 启动模块中的主类：如果给定了*mainclass*，则执行*mainclass*指定模块中的主类；如果没有给定，则执行模块中的值。换句话说，当模块未指定 mainclass 时，可以使用它；当模块指定了*mainclass*时，可以覆盖它的值。
  ```bash
  java [options] -m module[/mainclass] [args ...]
  ```
  或者
  ```bash
  java [options] --module module[/mainclass] [args ...]
  ```
- 启动单个源文件程序：
  仅用于启动单个源文件程序。使用源文件模式时指定包含主类的源文件。请参阅[使用源文件模式启动单文件源代码程序](https://docs.oracle.com/en/java/javase/11/tools/java.html#GUID-3B1CE181-CD30-4178-9602-230B800D4FAE__USINGSOURCE-FILEMODETOLAUNCHSINGLE--B5E57618)。
  ```bash
  java [options] source-file [args...] 
  ```

#### JDK_JAVA_OPTIONS
您可以使用`JDK_JAVA_OPTIONS`启动器环境变量，将其内容预置到`java`启动器的实际命令行中。

##### 例如
```bash
export JDK_JAVA_OPTIONS='-g @file1 -Dprop=value @file2 -Dws.prop="white spaces"' 
$ java -Xint @file3
```
等同于命令行：
```bash
java -g @file1 -Dprop=value @file2 -Dws.prop="white spaces" -Xint @file3
```

## Java选项
java 命令支持以下各类选项：
- 标准的Java选项：Java虚拟机的所有实现都保证支持这些选项。它们用于常见操作，例如检查 JRE 的版本、设置类路径、启用详细输出等。使用`java -help`命令输出标准选项。
- 额外的Java选项：特定于Java HotSpot虚拟机的通用选项。不保证所有JVM实现都支持它们，并且可能会发生变化。这些选项以-X开头，使用`java -X -help`命令输出标准选项。

不建议随意使用高级选项。这些是开发人员选项，用于调整Java HotSpot虚拟机操作的特定区域，这些区域通常具有特定的系统要求，并且可能需要对系统配置参数的特权访问。性能调整示例中提供了几个性能调整的示例。不保证所有JVM实现都支持这些选项，并且可能会发生变化。高级选项以`-XX`开头。
- 高级运行时的Java选项：控制Java HotSpot虚拟机的运行时行为。
- 高级JIT编译器的Java选项: 控制Java HotSpot虚拟机执行的即时（JIT）编译。
- 高级可维护性的Java选项：启用收集系统信息和执行大量调试的功能。
- 高级垃圾收集的Java选项：控制Java HotSpot如何执行垃圾收集（GC）。

布尔选项用于启用默认情况下禁用的功能或禁用默认情况下启用的功能。此类选项不需要参数。布尔-XX选项使用加号（-XX:+OptionName）启用，使用减号（-XX:-OptionName）禁用。

对于需要参数的选项，参数可以用空格、冒号（:）或等号（=）与选项名隔开，或者参数可以直接跟在选项后面（每个选项的具体语法都不同）。如果要以字节为单位指定大小，可以不使用后缀，或者使用后缀k或K表示千字节（KB），m或M表示兆字节（MB），g或G表示千兆字节（GB）。例如，要将大小设置为8GB，可以指定8g、8192m、8388608k或8589934592作为参数。如果需要指定百分比，则使用0至1之间的数字。例如，指定0.25表示25%。

### 标准的Java选项
这些是所有JVM实现都支持的最常用选项。
> 要为长选项指定参数，可以使用 --name=value 或 --name value。

### 额外的Java选项
##### -Xlog:option
使用Java虚拟机 (JVM) 统一日志记录框架配置或启用日志记录。请参阅[使用JVM统一日志记录框架启用日志记录](https://docs.oracle.com/en/java/javase/11/tools/java.html#GUID-BE93ABDC-999C-4CB5-A88B-1994AAAC74D5)。

##### -Xloggc:option
启用JVM统一日志记录框架。将GC状态记录到带有时间戳的文件中。

##### -Xmn size
设置年轻代堆的初始大小和最大大小（以字节为单位）。用字母k或K表示千字节，用m或M表示兆字节，用g或G表示千兆字节。堆的新生代区域用于存放新对象。在该区域执行GC的频率高于其他区域。如果年轻代的大小太小，就会执行很多次要的垃圾回收。如果大小过大，则只执行完全垃圾回收（full GC），这可能需要很长时间才能完成。**Oracle建议，年轻代的大小应大于堆总大小的25%，小于50%** 。下面的示例展示了如何使用各种单位将年轻代的初始大小和最大大小设置为256MB：
```
-Xmn256m
-Xmn262144k
-Xmn268435456
```
你可以使用`-XX:NewSize`来设置初始大小，使用`-XX:MaxNewSize`来设置最大大小，而不是使用`-Xmn`选项来设置新生代堆的初始大小和最大大小。
> 注意：年轻代，也有翻译成新生代。

##### -Xms size
设置堆的最小和初始大小（以字节为单位）。该值必须是1024的倍数且大于1MB。用字母k或K表示千字节，用m或M表示兆字节，用g或G表示千兆字节。下面的示例展示了如何使用各种单位将分配内存的大小设置为6MB：
```
-Xms6291456
-Xms6144k
-Xms6m
```
**如果将此选项设置为0，则初始大小将设置为分配给老年代和年轻代的大小之和，即`-Xms = -XX:NewSize + -XX:OldSize`），-Xmx同理**。可以使用-Xmn选项或-XX:NewSize选项设置年轻代堆的初始大小。
> - 选项`-XX:InitalHeapSize`也可以用来设置初始堆大小。如果该选项出现在命令行的-Xms之后，那么初始堆大小将被设置为-XX:InitalHeapSize指定的值。
> - 注意，它会覆盖`-XX:InitialRAMPercentage`。

##### -Xmx size
指定内存分配池的最大大小（以字节为单位），单位为字节。**此值必须是1024的倍数并且大于2MB**。附加字母k或k表示千字节，m或m表示兆字节，g或g表示千兆字节。默认值是在运行时根据系统配置选择的。对于服务器部署，-Xms和-Xmx通常设置为相同的值。以下示例显示了如何使用各种单位将已分配内存的最大允许大小设置为80MB：
```
-Xmx83886080
-Xmx81920k
-Xmx80m
```
> - -Xmx选项等同于于`-XX:MaxHeapSize`。
> - 注意，它会覆盖`-XX:MaxRAMPercentage`。

##### -XshowSettings:category
显示设置，该选项的可能类别参数如下：
- all：显示所有类别的设置。这是默认值。
- locale：显示与区域设置相关的设置。
- properties：显示与系统属性相关的设置。
- vm：显示JVM的设置。
- system：**Linux**：显示主机系统或容器配置。
```shell
# 显示程序的VM设置
java -XshowSettings:vm -version
```

##### -Xss size
设置线程堆栈大小（以字节为单位）。附加字母k或k表示KB，m或m表示MB，g或g表示GB。默认值取决于平台：
- Linux/x64 (64-bit): 1024 KB
- macOS (64-bit): 1024 KB
- Oracle Solaris/x64 (64-bit): 1024 KB
- Windows: 默认值取决于虚拟内存

以下示例以不同的单位将线程堆栈大小设置为1024 KB：
```
-Xss1m
-Xss1024k
-Xss1048576
```
此选项类似于`-XX:ThreadStackSize`。

#### 适用于macOS的额外选项
以下是macOS专用的额外选项。

##### -XstartOnFirstThread
在第一个（AppKit）线程上运行main()方法。

##### -Xdock:name=application_name
覆盖dock中显示的默认应用程序名称。

##### -Xdock:icon=path_to_icon_file
覆盖dock中显示的默认图标。

### Java高级运行时选项
这些Java选项控制Java HotSpot虚拟机的运行时行为。

##### -XX:ActiveProcessorCount=x
覆盖虚拟机将用于计算用于各种操作（如垃圾回收和ForkJoinPool）的线程池大小的CPU数。

虚拟机通常会从操作系统中确定可用处理器的数量。在 docker 容器中运行多个 Java 进程时，该标记可用于划分CPU资源。即使未启用UseContainerSupport，也会启用该标记。有关启用和禁用容器支持的说明，请参阅`-XX:-UseContainerSupport`。

##### -XX:MaxDirectMemorySize=size（俗称堆外内存）
设置`java.nio`包直接缓冲区分配的最大总大小（以字节为单位）。用字母k或K表示千字节，用m或M表示兆字节，用g或G表示千兆字节。默认情况下，大小设置为0，这意味着JVM会自动选择NIO直接缓冲区分配的大小。

以下示例说明了如何以不同单位将 NIO 大小设置为 1024 KB：
```
-XX:MaxDirectMemorySize=1m
-XX:MaxDirectMemorySize=1024k
-XX:MaxDirectMemorySize=1048576
```
**参考文档**
- [JVM源码分析之堆外内存完全解读](http://lovestblog.cn/blog/2015/05/12/direct-buffer/)

##### -XX:OnError=string
设置在发生不可恢复的错误时运行的自定义命令或一系列以分号分隔的命令。如果字符串包含空格，则必须将其括在引号中。
- Oracle Solaris、Linux、macOS：以下示例展示了如何使用`-XX:OnError`选项运行gcore命令来创建核心映像，并在发生不可恢复的错误时启动调试器附加到进程（%p表示当前进程）：
  ```
  -XX:OnError="gcore %p;dbx - %p"
  ```
- Windows：以下示例显示如何使用`-XX:OnError`选项运行userdump.exe实用程序来获取故障转储，以防出现不可恢复的错误（%p表示当前进程）。此示例假设在`PATH`环境变量中指定了userdump.exe实用程序的路径：
```
-XX:OnError="userdump.exe %p"
```

##### -XX:OnOutOfMemoryError=string
设置一个自定义命令或一系列分号分隔的命令，以在首次引发`OutOfMemoryError`异常时运行。如果字符串包含空格，则必须用引号括起来。有关命令字符串的示例，请参阅`-XX:OnError`选项的说明。

##### -XX:+PrintFlagsInitial
```shell
# 打印所有的默认参数设置
java -XX:+PrintFlagsInitial -version
```

##### -XX:+PrintFlagsFinal
```shell
# 打印最终值，如果某个默认值被新值覆盖，显示新值
java -XX:+PrintFlagsFinal -version
```

##### -XX:+PrintCommandLineFlags
启用打印出现在命令行上的JVM标志（修改过的）。了解JVM设置的人体工程学值很有用，例如堆空间大小和所选的垃圾收集器。默认情况下，此选项处于禁用状态并且不会打印标志。
```shell
# 打印那些被新值覆盖的项
java -XX:+PrintCommandLineFlags -version
```

##### -XX:-UseContainerSupport
虚拟机现在提供了自动容器检测支持，允许虚拟机确定在docker容器中运行的Java进程可用的内存量和处理器数量。它使用这些信息来分配系统资源。此支持仅在Linux x64平台上可用。如果支持，则此标志的默认值为true，并且默认情况下启用容器支持。可以使用-XX:-UseContainerSupport禁用它。

统一日志记录可帮助诊断与此支持相关的问题。

使用`-Xlog:os+container=trace`最大限度地记录容器信息。有关使用统一日志记录的说明，请参阅[使用JVM统一日志记录框架启用日志记录](https://docs.oracle.com/en/java/javase/11/tools/java.html#GUID-BE93ABDC-999C-4CB5-A88B-1994AAAC74D5)。

##### -XX:VMOptionsFile=filename
允许用户在文件中指定 VM 选项，例如：`java -XX:VMOptionsFile=/var/my_vm_options HelloWorld`。

这种方式相比直接在命令行上设置大量的选项参数要简洁的多。

### Java高级动态即时编译器选项
这些java选项控制Java HotSpot VM执行的动态即时（JIT）编译。


### Java高级服务性选项
这些Java选项提供了收集系统信息和执行大量调试的能力。

##### -XX:+HeapDumpOnOutOfMemoryError
允许在抛出`java.lang.OutOfMemoryError`异常时使用堆分析器（HPROF）将Java堆转储到当前目录中的文件。您可以使用`-XX:HeapDumpPath`选项显式设置堆转储文件路径和名称。默认情况下，此选项被禁用，并且在抛出`OutOfMemoryError`异常时不会转储堆。

##### -XX:HeapDumpPath=path
当设置了`-XX:+HeapDumpOnOutOfMemoryError`选项时，设置写入堆分析器（HPROF）提供的堆转储的路径和文件名。默认情况下，该文件是在当前工作目录中创建的，名称为`java_pid＜pid＞.hprof`，其中`＜pid＞`是导致错误的进程的标识符。以下示例显示了如何显式设置默认文件（%p表示当前进程标识符）：
```shell
-XX:HeapDumpPath=./java_pid%p.hprof
```
- Oracle Solaris, Linux, and macOS: 以下示例显示如何将堆转储文件设置为`/var/log/java/java_heapdump.hprof`：
  ```shell
  -XX:HeapDumpPath=/var/log/java/java_heapdump.hprof
  ```
- Windows：以下示例显示了如何将堆转储文件设置为`C:/log/java/java_heapdump.log`：
  ```shell
  -XX:HeapDumpPath=C:/log/java/java_heapdump.log
  ```

##### -XX:+PrintFlagsRanges
打印指定的范围，并允许自动测试值。详见：https://docs.oracle.com/en/java/javase/11/tools/java.html#GUID-9569449C-525F-4474-972C-4C1F63D5C357
```shell
java -XX:+PrintFlagsRanges -version
```

### Java高级垃圾回收选项
这些Java选项控制Java HotSpot VM如何执行垃圾收集(GC)。
> 有关JVM一些默认值（垃圾回收器、堆、编译器等）的说明：https://docs.oracle.com/en/java/javase/11/gctuning/ergonomics.html#GUID-DA88B6A6-AF89-4423-95A6-BBCBD9FAE781

#### -XX:InitialHeapSize=size
设置内存分配池的初始大小（以字节为单位）。该值必须是0或1024的倍数且大于1MB。附加字母k或K表示千字节，m或M表示兆字节，附加字母g或G 表示千兆字节。默认值是在运行时根据系统配置选择的。请参阅`Java SE HotSpot虚拟机垃圾收集调优指南`中的[人体工程学](https://docs.oracle.com/en/java/javase/11/gctuning/ergonomics.html#GUID-DB4CAE94-2041-4A16-90EC-6AE3D91EC1F1)部分。

以下示例展示了如何使用各种单位将分配的内存大小设置为6MB：
```shell
-XX:InitialHeapSize=6291456
-XX:InitialHeapSize=6144k
-XX:InitialHeapSize=6m
```
如果将该选项设置为0，那么初始大小将被设置为分配给老一代和年轻一代的大小之和。新一代堆的大小可以使用`-XX:NewSize`选项来设置。
> 注意：-Xms 选项设置堆的最小堆大小和初始堆大小。如果`-Xms出现在命令行上的`-XX:InitialHeapSize`之后，则初始堆大小将设置为`-Xms`指定的值。

#### -XX:MaxHeapSize=size
设置内存分配池的最大大小（以字节为单位）。该值必须是1024的倍数且大于2MB。附加字母k或K表示千字节，m或M表示兆字节，附加字母g或G表示千兆字节。默认值是在运行时根据系统配置选择的。对于服务器部署，选项`-XX:InitialHeapSize`和`-XX:MaxHeapSize`通常设置为相同的值。

以下示例展示如何使用各种单位将分配内存的最大允许大小设置为80MB：
```shell
-XX:MaxHeapSize=83886080
-XX:MaxHeapSize=81920k
-XX:MaxHeapSize=80m
```
在Oracle Solaris 7和Oracle Solaris 8 SPARC平台上，该值的上限约为4,000MB（减去开销）。在 Oracle Solaris 2.6和x86平台上，上限约为2,000 MB（减去开销）。在 Linux 平台上，上限约为 2,000 MB（减去开销）。

#### -XX:MaxMetaspaceSize=size
设置可分配给类元数据的本地内存最大容量。默认情况下，大小不受限制。应用程序的元数据量取决于应用程序本身、其他运行中的应用程序以及系统可用内存量。

#### -XX:MaxNewSize=size
设置年轻一代（幼儿园）堆的最大大小（字节）。默认值的设置符合人体工程学。

#### -XX:MaxTenuringThreshold=threshold
设置用于自适应GC大小的最大保有阈值。最大值为15。并行（吞吐量）收集器的默认值为 15，CMS 收集器的默认值为 6。

下面的示例显示了如何将最大保有阈值设置为 10：
```
-XX:MaxTenuringThreshold=10
```

#### -XX:NewRatio=ratio
设置新生代和老年代大小之间的比率。默认情况下，此选项设置为 2。以下示例显示如何将 young-to-old 比率设置为 1：
```shell
-XX:NewRatio=1
```

#### -XX:NewSize=size
设置年轻代（托儿所）的堆的初始大小（以字节为单位）。附加字母k或K表示千字节，m或M表示兆字节，附加字母g或G表示千兆字节。

堆的年轻代区域用于新对象。 GC 在该区域中执行的频率高于其他区域。如果年轻代的大小太小，则会执行大量的Minor GC。如果大小太高，则仅执行完整 GC，这可能需要很长时间才能完成。 Oracle 建议您将年轻代的大小保持在总堆大小的 25% 以上且小于 50% 之间。

以下示例展示了如何使用各种单位将年轻代的初始大小设置为 256 MB：
```shell
-XX:NewSize=256m
-XX:NewSize=262144k
-XX:NewSize=268435456
```


## 附加项

### 一、关于JVM参数的设置（容器化）
参考疑难杂症下的说明：[如何正确的设置JVM参数](../../troubleshoot/jvm-options-setting.md)。

### 二、关于-XX:InitialRAMPercentage、-XX:MinRAMPercentage和-XX:MaxRAMPercentage的理解
**1. -XX:InitialRAMPercentage**：它表示堆的初始大小占总内存的百分比。例如，如果堆大小为100MB，而总内存为1GB，则堆的初始大小为10%（100MB），即100MB。
> 值得注意的是，当我们配置`-Xms`选项时，JVM 会忽略`-XX:InitialRAMPercentage`。

**2. -XX:MinRAMPercentage**：参数与其名称不同，它允许设置小量内存（小于125MB）的JVM的最大堆大小。

首先看先它的默认值：
```shell
$ docker run openjdk:8 java -XX:+PrintFlagsFinal -version | grep -E "MinRAMPercentage"
   double MinRAMPercentage                      = 50.000000                            {product}

openjdk version "1.8.0_292"
OpenJDK Runtime Environment (build 1.8.0_292-b10)
```
然后，我们使用该参数来设置总内存为100MB的JVM的最大堆大小：
```shell
$ docker run -m 100MB openjdk:8 java -XX:MinRAMPercentage=80.0 -XshowSettings:VM -version

VM settings:
Max. Heap Size (Estimated): 77.38M
Ergonomics Machine Class: server
Using VM: OpenJDK 64-Bit Server VM

openjdk version "1.8.0_292"
OpenJDK Runtime Environment (build 1.8.0_292-b10)
```
此外，JVM在为小型内存服务器或容器设置最大堆大小时会**忽略**`-XX:MaxRAMPercentage`参数：
```shell
$ docker run -m 100MB openjdk:8 java -XX:MinRAMPercentage=80.0 -XX:MaxRAMPercentage=50.0 -XshowSettings:vm -version
VM settings:
Max. Heap Size (Estimated): 77.38M
Ergonomics Machine Class: server
Using VM: OpenJDK 64-Bit Server VM

openjdk version "1.8.0_292"
OpenJDK Runtime Environment (build 1.8.0_292-b10)
```

**3. -XX:MaxRAMPercentage**：参数与其名称不同，它允许设置大量内存（大于125MB）的JVM的最大堆大小。

首先，看下它的默认值
```shell
$ docker run openjdk:8 java -XX:+PrintFlagsFinal -version | grep -E "MaxRAMPercentage"
   double MaxRAMPercentage                      = 25.000000                            {product}

openjdk version "1.8.0_292"
OpenJDK Runtime Environment (build 1.8.0_292-b10)
```
然后，对于总内存为 500 MB 的 JVM，我们可以使用该参数将最大堆大小设置为 60%：
```shell
$ docker run -m 500MB openjdk:8 java -XX:MaxRAMPercentage=60.0 -XshowSettings:vm -version
VM settings:
Max. Heap Size (Estimated): 290.00M
Ergonomics Machine Class: server
Using VM: OpenJDK 64-Bit Server VM

openjdk version "1.8.0_292"
OpenJDK Runtime Environment (build 1.8.0_292-b10)
```
同样，JVM会忽略大内存服务器或容器的`-XX:MinRAMPercentage`参数：
```shell
$ docker run -m 500MB openjdk:8 java -XX:MaxRAMPercentage=60.0 -XX:MinRAMPercentage=30.0 -XshowSettings:vm -version
VM settings:
Max. Heap Size (Estimated): 290.00M
Ergonomics Machine Class: server
Using VM: OpenJDK 64-Bit Server VM

openjdk version "1.8.0_292"
OpenJDK Runtime Environment (build 1.8.0_292-b10)
```
关于以上的研究，参考：[https://www.baeldung.com/java-jvm-parameters-rampercentage](https://www.baeldung.com/java-jvm-parameters-rampercentage)。另外关于上述的`125MB`为Oracle官网的说明。

另外，`-XX:MaxRAMFraction`选项也可以用来控制堆的大小，默认值为4，即堆的大小为总内存的1/4。

### 附录
1. **[Java Heap Sizing in a Container: Quickly and Easily](https://blogs.oracle.com/java/post/java-heap-sizing-in-a-container-quickly-and-easily)**
2. [How To Configure Java Heap Size Inside a Docker Container](https://www.baeldung.com/ops/docker-jvm-heap-size)
3. [如何在Docker容器内配置Java堆大小](https://cloud.tencent.com/developer/article/2242238)
4. [Xmx和Xms的大小是小于Docker容器以及Pod的大小的，为啥还是会出现OOMKilled？](https://juejin.cn/post/7183549109025013815)