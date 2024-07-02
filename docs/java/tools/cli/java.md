# java

> java命令启动Java应用程序。它通过启动Java虚拟机 (JVM)、加载指定的类并调用该类的 main()方法来完成此操作。
>
> 参考文档：https://docs.oracle.com/en/java/javase/11/tools/java.html#GUID-3B1CE181-CD30-4178-9602-230B800D4FAE

## 概要

Windows操作系统：javaw命令与java完全相同，只是javaw没有关联的控制台窗口。如果不希望出现命令提示窗口，请使用javaw。不过，如果启动失败，javaw
启动器会显示一个包含错误信息的对话框。

- 启动一个类文件：
  ```shell
  java [options] mainclass [args ...]
  ```
- 启动jar文件的主类：执行封装在JAR文件中的程序。jarfile参数是JAR文件的名称，其清单包含*Main-Class:classname*
  形式的行，该行使用`public static void main(String[] args)`方法定义类，该方法用作应用程序的起点。使用 -jar 时，指定的 JAR
  文件是所有用户类的源，其他类路径设置将被忽略。
  ```shell
  java [options] -jar jarfile [args ...]
  ```
- 启动模块中的主类：如果给定了*mainclass*，则执行*mainclass*指定模块中的主类；如果没有给定，则执行模块中的值。换句话说，当模块未指定
  mainclass 时，可以使用它；当模块指定了*mainclass*时，可以覆盖它的值。
  ```shell
  java [options] -m module[/mainclass] [args ...]
  ```
  或者
  ```shell
  java [options] --module module[/mainclass] [args ...]
  ```
- 启动单个源文件程序：
  仅用于启动单个源文件程序。使用源文件模式时指定包含主类的源文件。请参阅[使用源文件模式启动单文件源代码程序](https://docs.oracle.com/en/java/javase/11/tools/java.html#GUID-3B1CE181-CD30-4178-9602-230B800D4FAE__USINGSOURCE-FILEMODETOLAUNCHSINGLE--B5E57618)。
  ```shell
  java [options] source-file [args...] 
  ```

#### JDK_JAVA_OPTIONS

您可以使用`JDK_JAVA_OPTIONS`启动器环境变量，将其内容预置到`java`启动器的实际命令行中。

##### 例如

```shell
export JDK_JAVA_OPTIONS='-g @file1 -Dprop=value @file2 -Dws.prop="white spaces"' 
$ java -Xint @file3
```

等同于命令行：

```shell
java -g @file1 -Dprop=value @file2 -Dws.prop="white spaces" -Xint @file3
```

## Java选项

java 命令支持以下各类选项：

- 标准的Java选项：Java虚拟机的所有实现都保证支持这些选项。它们用于常见操作，例如检查 JRE
  的版本、设置类路径、启用详细输出等。使用`java -help`命令输出标准选项。
- 额外的Java选项：特定于Java
  HotSpot虚拟机的通用选项。不保证所有JVM实现都支持它们，并且可能会发生变化。这些选项以-X开头，使用`java -X -help`命令输出标准选项。

不建议随意使用高级选项。这些是开发人员选项，用于调整Java
HotSpot虚拟机操作的特定区域，这些区域通常具有特定的系统要求，并且可能需要对系统配置参数的特权访问。性能调整示例中提供了几个性能调整的示例。不保证所有JVM实现都支持这些选项，并且可能会发生变化。高级选项以`-XX`
开头。

- 高级运行时的Java选项：控制Java HotSpot虚拟机的运行时行为。
- 高级JIT编译器的Java选项: 控制Java HotSpot虚拟机执行的即时（JIT）编译。
- 高级服务性的Java选项：启用收集系统信息和执行大量调试的功能。
- 高级垃圾收集的Java选项：控制Java HotSpot如何执行垃圾收集（GC）。

布尔选项用于启用默认情况下禁用的功能或禁用默认情况下启用的功能。此类选项不需要参数。布尔-XX选项使用加号（-XX:
+OptionName）启用，使用减号（-XX:-OptionName）禁用。

对于需要参数的选项，参数可以用空格、冒号（:
）或等号（=）与选项名隔开，或者参数可以直接跟在选项后面（每个选项的具体语法都不同）。如果要以字节为单位指定大小，可以不使用后缀，或者使用后缀k或K表示千字节（KB），m或M表示兆字节（MB），g或G表示千兆字节（GB）。例如，要将大小设置为8GB，可以指定8g、8192m、8388608k或8589934592作为参数。如果需要指定百分比，则使用0至1之间的数字。例如，指定0.25表示25%。

### 标准的Java选项

这些是所有JVM实现都支持的最常用选项。
> 要为长选项指定参数，可以使用 --name=value 或 --name value。

##### -agentlib:libname[=options]

加载指定的本机代理库。在库名称之后，可以使用逗号分隔选项列表。

- **Oracle Solaris、Linux和macOS**：如果指定了-agentlib:foo选项，则JVM将尝试在`LD_library_PATH`
  系统变量指定的位置加载名为libfoo.so的库（在macOS上，此变量为`DYLD_LIBRARI_PATH`）。
- **Windows**：如果指定了-agentlib:foo选项，那么JVM将尝试在`PATH`系统变量指定的位置加载名为foo.dll的库。

以下示例显示了如何加载Java Debug Wire Protocol（JDWP）库并侦听端口8000上的套接字连接，从而在加载主类之前挂起JVM：

```shell
-agentlib:jdwp=transport=dt_socket,server=y,address=8000
````

##### -Dproperty=value

定义系统属性值。`property`变量是一个不带空位的字符串，代表属性的名称。`value`
变量是表示属性值的字符串。如果值是带空位的字符串，则用引号将其括起来（例如`-Dfoo="foo bar"`）。

##### -javaagent:jarpath[=options]

加载指定的Java编程语言代理。

##### -X

将有关额外选项的帮助打印到错误流中。

##### @argfile

指定Java命令使用的一个或多个以`@`为开头的参数文件。由于类路径中需要`.jar`文件，Java命令行非常长的情况并不罕见。`@argfile`
选项通过使启动器能够在shell扩展后但在参数处理之前扩展参数文件的内容来克服命令行长度限制。参数文件中的内容将被扩展，因为否则，它们将在命令行上指定，直到遇到`-Xdisable-@files`
选项。

参数文件还可以包含主类名和所有选项。如果参数文件包含java命令所需的所有选项，那么命令行可以简单地为：

```shell
java @argfile
```

有关使用`@argfile`的描述和示例，请参阅[java命令行参数文件](#java命令行参数文件)。

### 额外的Java选项

##### -Xlog:option

使用Java虚拟机 (JVM)
统一日志记录框架配置或启用日志记录。请参阅[使用JVM统一日志记录框架启用日志记录](https://docs.oracle.com/en/java/javase/11/tools/java.html#GUID-BE93ABDC-999C-4CB5-A88B-1994AAAC74D5)。

##### -Xloggc:option

启用JVM统一日志记录框架。将GC状态记录到带有时间戳的文件中。

##### -Xmn size

设置年轻代堆的初始大小和最大大小（以字节为单位）。用字母k或K表示千字节，用m或M表示兆字节，用g或G表示千兆字节。堆的新生代区域用于存放新对象。在该区域执行GC的频率高于其他区域。如果年轻代的大小太小，就会执行很多次要的垃圾回收。如果大小过大，则只执行完全垃圾回收（full
GC），这可能需要很长时间才能完成。**Oracle建议，年轻代的大小应大于堆总大小的25%，小于50%**
。下面的示例展示了如何使用各种单位将年轻代的初始大小和最大大小设置为256MB：

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

**如果将此选项设置为0，则初始大小将设置为分配给老年代和年轻代的大小之和，即`-Xms = -XX:NewSize + -XX:OldSize`），-Xmx同理**
。可以使用-Xmn选项或-XX:NewSize选项设置年轻代堆的初始大小。
> - 选项`-XX:InitalHeapSize`也可以用来设置初始堆大小。如果该选项出现在命令行的-Xms之后，那么初始堆大小将被设置为-XX:
    InitalHeapSize指定的值。
> - 注意，它会覆盖`-XX:InitialRAMPercentage`。

##### -Xmx size

指定内存分配池的最大大小（以字节为单位），单位为字节。**此值必须是1024的倍数并且大于2MB**
。附加字母k或k表示千字节，m或m表示兆字节，g或g表示千兆字节。默认值是在运行时根据系统配置选择的。对于服务器部署，-Xms和-Xmx通常设置为相同的值。以下示例显示了如何使用各种单位将已分配内存的最大允许大小设置为80MB：

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

虚拟机通常会从操作系统中确定可用处理器的数量。在 docker 容器中运行多个 Java
进程时，该标签可用于划分CPU资源。即使未启用UseContainerSupport，也会启用该标签。有关启用和禁用容器支持的说明，请参阅`-XX:-UseContainerSupport`。

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
- Windows：以下示例显示如何使用`-XX:OnError`
  选项运行userdump.exe实用程序来获取故障转储，以防出现不可恢复的错误（%p表示当前进程）。此示例假设在`PATH`
  环境变量中指定了userdump.exe实用程序的路径：

```
-XX:OnError="userdump.exe %p"
```

##### -XX:OnOutOfMemoryError=string

设置一个自定义命令或一系列分号分隔的命令，以在首次引发`OutOfMemoryError`
异常时运行。如果字符串包含空格，则必须用引号括起来。有关命令字符串的示例，请参阅`-XX:OnError`选项的说明。

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

虚拟机现在提供了自动容器检测支持，允许虚拟机确定在docker容器中运行的Java进程可用的内存量和处理器数量。它使用这些信息来分配系统资源。此支持仅在Linux
x64平台上可用。如果支持，则此标志的默认值为true，并且默认情况下启用容器支持。可以使用-XX:-UseContainerSupport禁用它。

统一日志记录可帮助诊断与此支持相关的问题。

使用`-Xlog:os+container=trace`
最大限度地记录容器信息。有关使用统一日志记录的说明，请参阅[使用JVM统一日志记录框架启用日志记录](https://docs.oracle.com/en/java/javase/11/tools/java.html#GUID-BE93ABDC-999C-4CB5-A88B-1994AAAC74D5)。

##### -XX:VMOptionsFile=filename

允许用户在文件中指定 VM 选项，例如：`java -XX:VMOptionsFile=/var/my_vm_options HelloWorld`。

这种方式相比直接在命令行上设置大量的选项参数要简洁的多。

### Java高级动态即时编译器选项

这些java选项控制Java HotSpot VM执行的动态即时（JIT）编译。

### Java高级服务性选项

这些Java选项提供了收集系统信息和执行大量调试的能力。

##### -XX:+HeapDumpOnOutOfMemoryError

允许在抛出`java.lang.OutOfMemoryError`
异常时使用堆分析器（HPROF）将Java堆转储到当前目录中的文件。您可以使用`-XX:HeapDumpPath`
选项显式设置堆转储文件路径和名称。默认情况下，此选项被禁用，并且在抛出`OutOfMemoryError`异常时不会转储堆。

##### -XX:HeapDumpPath=path

当设置了`-XX:+HeapDumpOnOutOfMemoryError`
选项时，设置写入堆分析器（HPROF）提供的堆转储的路径和文件名。默认情况下，该文件是在当前工作目录中创建的，名称为`java_pid＜pid＞.hprof`
，其中`＜pid＞`是导致错误的进程的标识符。以下示例显示了如何显式设置默认文件（%p表示当前进程标识符）：

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
>
有关JVM一些默认值（垃圾回收器、堆、编译器等）的说明：https://docs.oracle.com/en/java/javase/11/gctuning/ergonomics.html#GUID-DA88B6A6-AF89-4423-95A6-BBCBD9FAE781

#### -XX:InitialHeapSize=size

设置内存分配池的初始大小（以字节为单位）。该值必须是0或1024的倍数且大于1MB。附加字母k或K表示千字节，m或M表示兆字节，附加字母g或G
表示千兆字节。默认值是在运行时根据系统配置选择的。请参阅`Java SE HotSpot虚拟机垃圾收集调优指南`
中的[人体工程学](https://docs.oracle.com/en/java/javase/11/gctuning/ergonomics.html#GUID-DB4CAE94-2041-4A16-90EC-6AE3D91EC1F1)
部分。

以下示例展示了如何使用各种单位将分配的内存大小设置为6MB：

```shell
-XX:InitialHeapSize=6291456
-XX:InitialHeapSize=6144k
-XX:InitialHeapSize=6m
```

如果将该选项设置为0，那么初始大小将被设置为分配给老一代和年轻一代的大小之和。新一代堆的大小可以使用`-XX:NewSize`选项来设置。
> 注意：-Xms 选项设置堆的最小堆大小和初始堆大小。如果`-Xms出现在命令行上的`-XX:InitialHeapSize`之后，则初始堆大小将设置为`
> -Xms`指定的值。

#### -XX:MaxHeapSize=size

设置内存分配池的最大大小（以字节为单位）。该值必须是1024的倍数且大于2MB。附加字母k或K表示千字节，m或M表示兆字节，附加字母g或G表示千兆字节。默认值是在运行时根据系统配置选择的。对于服务器部署，选项`-XX:InitialHeapSize`
和`-XX:MaxHeapSize`通常设置为相同的值。

以下示例展示如何使用各种单位将分配内存的最大允许大小设置为80MB：

```shell
-XX:MaxHeapSize=83886080
-XX:MaxHeapSize=81920k
-XX:MaxHeapSize=80m
```

在Oracle Solaris 7和Oracle Solaris 8 SPARC平台上，该值的上限约为4,000MB（减去开销）。在 Oracle Solaris 2.6和x86平台上，上限约为2,000
MB（减去开销）。在 Linux 平台上，上限约为 2,000 MB（减去开销）。

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

堆的年轻代区域用于新对象。 GC 在该区域中执行的频率高于其他区域。如果年轻代的大小太小，则会执行大量的Minor GC。如果大小太高，则仅执行完整
GC，这可能需要很长时间才能完成。 Oracle 建议您将年轻代的大小保持在总堆大小的 25% 以上且小于 50% 之间。

以下示例展示了如何使用各种单位将年轻代的初始大小设置为 256 MB：

```shell
-XX:NewSize=256m
-XX:NewSize=262144k
-XX:NewSize=268435456
```

### java命令行参数文件

您可以使用`@argument_files`来指定一个或多个包含参数的文本文件，例如传递给`java`命令的选项和类名，从而缩短或简化`java`
命令。这使您可以在任何操作系统上创建任何长度的java命令。

在命令行中，使用at符号（`@`）前缀来标识包含java选项和类名的参数文件。当java命令遇到一个以at符号（`@`
）开头的文件时，它会将该文件的内容扩展到参数列表中，就像在命令行中指定的那样。

java启动器将展开参数文件内容，直到遇到`-Xdisable-@files`
选项。您可以在命令行的任何位置（包括参数文件中）使用`-Xdisable-@files`选项来停止`@argument_files`扩展。

以下项目描述了java参数文件的语法：

- 参数文件必须仅包含ASCII字符或ASCII友好的系统默认编码中的字符，如UTF-8。
- 参数文件大小不得超过MAXINT (2,147,483,647) 字节。
- 启动器不会扩展存在于参数文件中的通配符。
- 使用空格或换行符分隔文件中包含的参数。
- 空白包括空白字符`\t`、`\n`、`\r`和`\f`。
  例如，可以有一个带有空格的路径，例如`c:\Program Files`，可以指定为`"c:\\Program Files"`
  ，或者为避免转义，可以指定`c:\Program" "Files`。
- 任何包含空白的选项（例如路径组件）都必须位于使用引号（`""`）字符的引号内。
- 引号内的字符串可能包含字符`\n`、`\r`、`\t`和`\f`。它们被转换为各自的ASCII代码。
- 如果文件名包含嵌入的空格，请将整个文件名用双引号括起来。
- 参数文件中的文件名是相对于当前目录的，而不是相对于参数文件的位置的。
- 使用参数文件中的数字符号（`#`）来标识注释。#后面的所有字符都将被忽略，直到行结束。
- 前缀选项的附加at符号（`@`）前缀充当转义符（第一个`@`被删除，其余参数直接显示给启动器）。
- 可以使用行尾的延续字符（`\`
  ）来延续行。这两条线相连，并修剪了前面的空白。为了防止修剪前面的空白，可以在第一列放置延续字符（`\`）。
- 因为反斜杠（`\`）是一个转义符，所以反斜杠字符必须用另一个反斜杠字符转义。
- 允许使用部分引号，并以文件结尾结束。
- 除非`\`是最后一个字符，否则开放式引号将在行尾停止，然后通过删除所有前导空格字符来加入下一行。
- 这些列表中不允许使用通配符（*）（例如指定`*.java`）。
- 不支持使用at符号（`@`）递归解释文件。

#### 参数文件中的开放引号或部分引号示例

在参数文件中，

```shell
-cp "lib/
cool/
app/
jars
```

这被解释为：

```
-cp lib/cool/app/jars
```

#### 参数文件中反斜线字符与另一个反斜线字符一起转义的示例

输出以下内容:

```shell
-cp c:\Program Files (x86)\Java\jre\lib\ext;c:\Program Files\Java\jre9\lib\ext
```

反斜杠字符必须在参数文件中指定为:

```shell
-cp  "c:\\Program Files (x86)\\Java\\jre\\lib\\ext;c:\\Program Files\\Java\\jre9\\lib\\ext"
```

#### 用于强制参数文件中的行连接的EOL转义示例

在参数文件中，

```shell
-cp "/lib/cool app/jars:\
    /lib/another app/jars"
```

这被解释为：

```shell
-cp /lib/cool app/jars:/lib/another app/jars
```

#### 参数文件中带前导空格的换行示例

在参数文件中，

```shell
-cp "/lib/cool\ 
\app/jars” 
```

这被解释为：

```shell
-cp /lib/cool app/jars
```

#### 使用单个参数文件的示例

您可以使用单个参数文件 (如以下示例中的`myargumentfile`) 来保存所有必需的java参数：

```shell
java @myargumentfile
```

#### 使用带路径的参数文件的示例

可以在参数文件中包含相对路径；但是，它们是相对于当前工作目录的，而不是相对于参数文件本身的路径。在以下示例中，`path1/options`
和`path2/options`表示具有不同路径的参数文件。它们包含的任何相对路径都是相对于当前工作目录的，而不是相对于参数文件的：

```shell
java @path1/options @path2/classes
```

### 代码堆状态分析

在某些情况下，深入了解JVM代码堆的当前状态将有助于回答以下问题：

- 为什么JIT被关闭，然后一次又一次地打开？
- 所有的代码堆空间都去了哪里？
- 为什么方法清除器不能有效工作？

为了提供这一见解，已经实现了一个代码堆状态分析功能，可以对代码堆进行实时分析。分析过程分为两个部分。第一部分检查整个代码堆，并聚合所有被认为有用或重要的信息。第二部分由几个独立的步骤组成，这些步骤打印收集的信息，重点是数据的不同方面。数据收集和打印是在“应要求”的基础上进行的。

**语法**
可以使用以下命令发出实时动态分析请求：

```
jcmd pid Compiler.CodeHeap_Analytics [function] [granularity]
```

如果您只对运行示例工作负载后的代码堆外观感兴趣，则可以使用命令行选项：

```
-Xlog:codecache=Trace
```

若要在存在“CodeCache已满”条件时查看代码堆状态，请使用命令行选项启动VM：

```
-Xlog:codecache=Debug
```

有关代码堆状态分析功能、支持的功能和粒度选项的详细描述，请参阅[CodeHeap State Analytics（OpenJDK）](https://bugs.openjdk.java.net/secure/attachment/75649/JVM_CodeHeap_StateAnalytics_V2.pdf)。

### 使用JVM统一日志框架启用日志

您可以使用`-Xlog`选项通过JVM统一日志框架配置或启用日志记录。

#### 概要

```
-Xlog[:[what][:[output][:[decorators][:output-options[,...]]]]]
```

- what：指定标签和级别的组合，格式为 tag1 [+ tag2 ...] [ * ][= level ][，...].除非指定了标签符（*
  ），否则只匹配与指定标签符完全一致的日志消息。请参见-Xlog标签和级别。
- output：设置输出类型。省略 output 类型默认为 stdout 。请参见-Xlog输出。
- decorators：配置输出以使用一组自定义的装饰器。省略decorators默认为uptime、level和tags 。这是装饰。
- output-options：设置`-Xlog`日志输出选项。

#### 描述

Java虚拟机（JVM）统一日志框架为JVM的所有组件提供了一个通用的日志系统。JVM的GC日志已更改为使用新的日志框架。将旧的GC标志映射到相应的新Xlog配置的方法在“[将GC日志标志转换为Xlog](#将GC日志标志转换为Xlog)
”中描述。此外，运行时日志记录也已更改为使用JVM统一日志框架。将传统运行时日志标志映射到相应的新Xlog配置的方法在将“[运行时日志标志转换为Xlog](#将运行时日志标志转换为Xlog)
”中描述。

下面提供了 -Xlog 命令和选项语法的快速参考：

##### -Xlog

启用info级别的JVM日志。

##### -Xlog:help

打印-Xlog使用语法和可用的标签、级别和装饰器，以及带有说明的示例命令行。

##### -Xlog:disable

关闭所有日志记录并清除日志记录框架的所有配置，包括警告和错误的默认配置。

##### -Xlog[:option]

按多个参数在命令行上的显示顺序应用这些参数。同一输出的多个-Xlog参数按给定顺序相互覆盖。

`option`设置为：

```
[tag selection][:[output][:[decorators][:output-options]]]
```

省略`tag selection`会默认为`all`标签集和`info`级别。

```
tag[+...] all
```

`all`标签是一个元标签，包含所有可用的标签集合。在标签集定义中，星号“*
”表示通配符标签匹配。使用通配符进行匹配会选择包含至少指定标签的所有标签集。如果没有使用通配符，只会选择与指定标签集完全匹配的项。

`output_options`是

```
filecount=file-count filesize=file size with optional K, M or G suffix
```

#### 默认配置

如果在命令行中未指定 -Xlog 选项，则使用默认配置。默认配置记录所有消息，其级别与警告或错误相匹配，而不管消息与什么标签相关联。默认配置相当于在命令行中输入以下内容：

```
-Xlog:all=warning:stdout:uptime,level,tags
```

#### 控制日志记录

日志记录也可以通过诊断命令（使用`jcmd`实用工具）在运行时进行控制。所有可以在命令行上指定的内容也可以通过`VM.log`
命令动态指定。由于诊断命令自动公开为MBeans，您可以使用JMX在运行时更改日志记录配置。

#### -Xlog标签和级别

每条日志消息都有与之关联的级别和标签集。消息的级别对应其详细信息，而标签集对应消息的内容或涉及的JVM组件（例如GC、编译器或线程）。将GC标志映射到Xlog配置的方法在“[将GC日志标志转换为Xlog](#将GC日志标志转换为Xlog)
”中描述。将传统运行时日志标志映射到相应Xlog配置的方法在“[运行时日志标志转换为Xlog](#将运行时日志标志转换为Xlog)”中描述。

##### 可用日志级别：

- off
- trace
- debug
- info
- warning
- error

##### 可用的日志标签：

以下是可用的日志标签。指定“all”而不是标签组合将匹配所有的标签组合。

```
add, age, alloc, annotation, arguments, attach, barrier, blocks, bot, breakpoint, bytecode, cds, census, class, classhisto, cleanup, codecache, compaction, compilation, condy, constantpool, constraints, container, continuations, coops, cpu, cset, data, datacreation, dcmd, decoder, defaultmethods, deoptimization, dependencies, director, dump, dynamic, ergo, event, exceptions, exit, fastlock, finalizer, fingerprint, free, freelist, gc, handshake, hashtables, heap, humongous, ihop, iklass, indy, init, inlining, install, interpreter, itables, jfr, jit, jni, jvmci, jvmti, lambda, library, liveness, load, loader, logging, malloc, map, mark, marking, membername, memops, metadata, metaspace, methodcomparator, methodhandles, mirror, mmu, module, monitorinflation, monitormismatch, nestmates, nmethod, nmt, normalize, numa, objecttagging, obsolete, oldobject, oom, oopmap, oops, oopstorage, os, owner, page, pagesize, parser, patch, path, perf, periodic, phases, plab, placeholders, preempt, preorder, preview, promotion, protectiondomain, ptrqueue, purge, record, redefine, ref, refine, region, reloc, remset, resolve, safepoint, sampling, scavenge, sealed, setting, smr, stackbarrier, stackmap, stacktrace, stackwalk, start, startup, startuptime, state, stats, streaming, stringdedup, stringtable, stubs, subclass, survivor, suspend, sweep, symboltable, system, table, task, thread, throttle, timer, tlab, tracking, unload, unmap, unshareable, update, valuebasedclasses, verification, verify, vmmutex, vmoperation, vmthread, vtables, vtablestubs
```

下表描述了标签的可能组合以及日志级别的列表。

| 日志标签                                                               | 描述                                                       |
|--------------------------------------------------------------------|----------------------------------------------------------|
| -Xlog:gc                                                           | 打印`gc`信息以及发生垃圾回收的时间。                                     |
| -Xlog:gc*                                                          | 打印包含至少`gc`标签的日志消息。它也可能包含与之相关的其他标签。但不会提供`phrase`级别的信息。    |
| -Xlog:gc*=trace                                                    | 打印至少包括`gc`标签的日志消息。它还可以具有与之关联的其他标签。但是，它不会提供`phrase`级别的信息。 |
| -Xlog:gc+phases=debug                                              | 打印不同的`phrase`级别信息。这提供了`debug`级别记录的详细信息。                  |
| -Xlog:gc+heap=debug                                                | 打印GC前后的堆使用详细信息。这会以`debug`级别记录带有`gc`和堆标签的消息。              |
| -Xlog:safepoint                                                    | 打印同一级别的应用程序并发时间和应用程序停止时间的详细信息。                           |
| -Xlog:gc+ergo*=trace                                               | 打印级别的`gc`和`ergo`消息的组合。信息包括有关堆大小和收集设置构建的所有细节。             |
| -Xlog:gc+age=trace                                                 | 打印`trace`级别的存活对象在幸存者空间中的幸存大小和年龄分布。                       |
| -Xlog:gc*:file=<file>::filecount=<count>,filesize=<filesize in kb> | 将输出重定向到文件，指定要使用的文件数以及文件大小（以KB为单位）。                       |

#### -Xlog输出

`-Xlog`选项支持以下类型的输出：

- stdout：将输出发送到stdout。
- stderr：将输出发送到stderr。
- file=filename：将输出发送到文本文件。

使用`file=filename`时，如果在文件名中指定`%p`和或`%t`，则分别会扩展为JVM的PID（进程ID）和启动时间戳。您还可以根据文件大小和要保留的文件数量配置文本文件进行文件轮换。例如，要每10
MB轮换日志文件并保留5个轮换文件，需指定选项 `filesize=10M, filecount=5`
。文件的目标大小不能保证完全准确，只是一个近似值。默认情况下，文件会以最多5个轮换文件的目标大小约20
MB进行轮换，除非另有配置。指定 `filecount=0` 表示不应轮换日志文件。有可能会覆盖预先存在的日志文件。

#### 修饰

日志消息会被与消息相关的信息所修饰。你可以为每种输出配置一个自定义的修饰符集。输出的顺序始终与表中列出的顺序相同。你可以在运行时配置所使用的修饰符。修饰符会被添加在日志消息的前面。例如：

```
[6.567s][info][gc,old] Old collection complete
```

省略`decorators`默认为`uptime`、`level`和`tags`。`none`修饰器是特殊的，用于关闭所有修饰。

**表2-1 可能的日志消息修饰**

| 修饰                 | 描述                                   |
|--------------------|--------------------------------------|
| time or t          | ISO-8601格式的当前时间和日期。                  |
| utctime or utc     | 世界协调时间（UTC）。                         |
| uptime or u        | 自JVM启动以来的时间，以秒和毫秒为单位。例如，6.567s。      |
| timemillis or tm   | 与`System.currentTimeMillis()`生成的值相同。 |
| uptimemillis or um | 自JVM启动以来的毫秒数。                        |
| timenanos or tn    | 与`System.nanoTime()`生成的值相同。          |
| uptimenanos or un  | 自JVM启动以来的纳秒数。                        |
| hostname or hn     | 主机名。                                 |
| pid or p           | 进程标识符。                               |
| tid or ti          | 线程标识符。                               |
| level or l         | 与日志消息关联的级别。                          |
| tags or tg         | 与日志消息关联的标签集。                         |

#### 将GC日志标志转换为Xlog

**表2-2 将旧版垃圾收集日志记录标志映射到Xlog配置**

| 旧版垃圾回收 (GC) 标志                     | Xlog配置                      | 注释                                                                                              |
|------------------------------------|-----------------------------|-------------------------------------------------------------------------------------------------|
| G1PrintHeapRegions                 | -Xlog:gc+region=trace       | 不适用                                                                                             |
| GCLogFileSize                      | 没有可用的配置                     | 日志轮换由框架处理。                                                                                      |
| NumberOfGCLogFiles                 | 不适用                         | 日志轮换由框架处理。                                                                                      |
| PrintAdaptiveSizePolicy            | -Xlog:gc+ergo*=level        | 对大多数信息使用调试级别，或者对为`PrintAdaptiveSizePolicy`记录的所有内容使用跟踪级别。                                        |
| PrintGC                            | -Xlog:gc                    | 不适用                                                                                             |
| PrintGCApplicationConcurrentTime   | -Xlog:safepoint             | 请注意，`PrintGCApplicationConcurrentTime`和`PrintGCApplicationStoppedTime`记录在同一个标签上，并且在新的日志记录中没有分开。 |
| PrintGCApplicationStoppedTime      | -Xlog:safepoint             | 请注意，`PrintGCApplicationConcurrentTime`和`PrintGCApplicationStoppedTime`记录在同一个标签上，而不是在新的日志记录中分开。  |
| PrintGCCause                       | 不适用                         | 现在始终被记录GC原因。                                                                                    |
| PrintGCDateStamps                  | 不适用                         | 日期戳由框架记录。                                                                                       |
| PrintGCDetails                     | -Xlog:gc*                   | 不适用                                                                                             |
| PrintGCID                          | 不适用                         | 现在始终记录GC ID。                                                                                    |
| PrintGCTaskTimeStamps              | -Xlog:gc+task*=debug        | 不适用                                                                                             |
| PrintGCTimeStamps                  | 不适用                         | 时间戳由框架记录。                                                                                       |
| PrintHeapAtGC                      | -Xlog:gc+heap=trace         | 不适用                                                                                             |
| PrintReferenceGC                   | -Xlog:gc+ref*=debug         | 请注意，在旧的日志记录中，只有在启用了`PrintGCDetails`时，`PrintReferenceGC`才有效。                                     |
| PrintStringDeduplicationStatistics | -Xlog:gc+stringdedup*=debug | 不适用                                                                                             |
| PrintTenuringDistribution          | -Xlog:gc+age*=level         | 对最相关的信息使用调试级别，或对为PrintTenuringDistribution记录的所有内容使用跟踪级别。                                        |
| UseGCLogFileRotation               | 不适用                         | 为`PrintTenuringDistribution`记录了什么。                                                              |

#### 将运行时日志标志转换为Xlog

**表2-3 将运行时日志记录标志映射到Xlog配置**

| Legacy Runtime Flag       | Xlog Configuration                  | Comment                                                                                                             |
|---------------------------|-------------------------------------|---------------------------------------------------------------------------------------------------------------------|
| TraceExceptions           | -Xlog:exceptions=info               | 不适用                                                                                                                 |
| TraceClassLoading         | -Xlog:class+load=level              | 使用`level=info`获取常规信息，或使用`level=debug`获取额外信息。在统一日志记录语法中，`-verbose:class`等于`-Xlog:class+load=info,class+unload=info`。 |
| TraceClassLoadingPreorder | -Xlog:class+preorder=debug          | 不适用                                                                                                                 |
| TraceClassUnloading       | -Xlog:class+unload=level            | 使用`level=info`获取常规信息，或使用`level=trace`获取额外信息。在统一日志记录语法中，`-verbose:class`等于`-Xlog:class+load=info,class+unload=info`。 |
| VerboseVerification       | -Xlog:verification=info             | 不适用                                                                                                                 |
| TraceClassPaths           | -Xlog:class+path=info               | 不适用                                                                                                                 |
| TraceClassResolution      | -Xlog:class+resolve=debug           | 不适用                                                                                                                 |
| TraceClassInitialization  | -Xlog:class+init=info               | 不适用                                                                                                                 |
| TraceLoaderConstraints    | -Xlog:class+loader+constraints=info | 不适用                                                                                                                 |
| TraceClassLoaderData      | -Xlog:class+loader+data=level       | 使用`level=debug`获取常规信息，使用`level=trace`获取额外信息。                                                                        |
| TraceSafepointCleanupTime | -Xlog:safepoint+cleanup=info        | 不适用                                                                                                                 |
| TraceSafepoint            | -Xlog:safepoint=debug               | 不适用                                                                                                                 |
| TraceMonitorInflation     | -Xlog:monitorinflation=debug        | 不适用                                                                                                                 |
| TraceBiasedLocking        | -Xlog:biasedlocking=level           | 使用`level=info`获取常规信息，使用`level=trace`获取额外信息。                                                                         |
| TraceRedefineClasses      | -Xlog:redefine+class*=level         | 使用`level=info`、`level=debug`和`level=trace`提供越来越多的信息。                                                                |

### -Xlog使用示例

以下是-Xlog示例。

##### Xlog
使用`info`级别将所有消息记录到`stdout`，并附带`uptime`、`level`和`tags`等修饰。这等同于使用以下命令：
```
-Xlog:all=info:stdout:uptime,levels,tags
```

##### -Xlog:gc
使用信息级别将带有`gc`标签的消息记录到`stdout`。处于警告级别的所有其他消息的默认配置有效。

##### -Xlog:gc,safepoint
使用默认修饰将标签有`gc`或`safepoint`标签的消息（均使用信息级别）记录到`stdout`。标签有`gc`和`safepoint`的消息将不会被记录。

##### -Xlog:gc+ref=debug
使用调试级别将同时带有`gc`和`ref`标签的日志消息打印到标准输出，使用默认的修饰。只带有其中一个标签的消息将不会被记录。

##### -Xlog:gc=debug:file=gc.txt:none
使用调试级别将带有`gc`标签的消息记录到一个名为`gc.txt`的文件中，该文件没有任何修饰。所有其他处于警告级别的消息的默认配置仍然有效。

##### -Xlog:gc=trace:file=gctrace.txt:uptimemillis,pids:filecount=5,filesize=1024
使用跟踪级别将带有`gc`标签，并使用修饰`uptimemillis`和`pid`的日志记录到包含5个大小为1MB的名称为`gctrace.txt`文件的旋转文件集中。对于所有其他级别为警告的消息，默认配置仍然生效。

##### -Xlog:gc::uptime,tid
使用默认的`info`级别将带有`gc`标签的日志消息输出到默认的`stdout`，并使用`uptime`和`tid`修饰。对于所有其他级别为警告的消息，默认配置仍然生效。

##### -Xlog:gc*=info,safepoint*=off
使用`info`级别记录至少带有`gc`标签的消息，但是关闭了带有`safepoint`标签的消息的记录。同时带有`gc`和`safepoint`标签的消息将不会被记录。

##### -Xlog:disable -Xlog:safepoint=trace:safepointtrace.txt
关闭所有日志记录，包括警告和错误，然后使用文件`safepoint trace.txt`的跟踪级别启用标签有`safepoint`的消息。默认配置不适用，因为命令行以`-Xlog:disable`开头。

### 复杂-Xlog用法示例
下面描述了一些使用-Xlog选项的复杂示例。

##### -Xlog:gc+class*=debug
使用调试级别将至少带有`gc`和`class`标签的消息记录到`stdout`。处于警告级别的所有其他消息的默认配置仍然有效。

##### -Xlog:gc+meta*=trace,class*=off:file=gcmetatrace.txt
使用跟踪级别将至少带有`gc`和`meta`标签的消息记录到文件`metatrace.txt`，但关闭所有带有`class`标签的消息。使用`gc`、`meta`和`class`标签的消息不会被记录，因为`class*`设置为`off`。除包含`class`的消息外，所有其他处于警告级别的消息的默认配置都有效。

##### -Xlog:gc+meta=trace
使用跟踪级别将使用`gc`和`meta`标签的消息记录到`stdout`。所有其他处于警告级别的消息的默认配置仍然有效。

##### -Xlog:gc+class+heap*=debug,meta*=warning,threads*=off
使用跟踪级别将至少标记有`gc`、`class`和`heap`标签的消息记录到`stdout`，但仅记录标记有`meta`和`level`的消息。警告级别的所有其他消息的默认配置都有效，但包括线程的消息除外。

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

关于以上的研究，参考：[https://www.baeldung.com/java-jvm-parameters-rampercentage](https://www.baeldung.com/java-jvm-parameters-rampercentage)
。另外关于上述的`125MB`为Oracle官网的说明。

另外，`-XX:MaxRAMFraction`选项也可以用来控制堆的大小，默认值为4，即堆的大小为总内存的1/4。

### 附录

1.
    *

*[Java Heap Sizing in a Container: Quickly and Easily](https://blogs.oracle.com/java/post/java-heap-sizing-in-a-container-quickly-and-easily)
**

2. [How To Configure Java Heap Size Inside a Docker Container](https://www.baeldung.com/ops/docker-jvm-heap-size)
3. [如何在Docker容器内配置Java堆大小](https://cloud.tencent.com/developer/article/2242238)
4. [Xmx和Xms的大小是小于Docker容器以及Pod的大小的，为啥还是会出现OOMKilled？](https://juejin.cn/post/7183549109025013815)