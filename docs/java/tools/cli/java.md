# java
> 启动一个Java应用。

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

## Java选项
java 命令支持以下各类选项：
- 标准的Java选项：Java虚拟机的所有实现都保证支持这些选项。它们用于常见操作，例如检查 JRE 的版本、设置类路径、启用详细输出等。
- 额外的Java选项：特定于Java HotSpot虚拟机的通用选项。不保证所有JVM实现都支持它们，并且可能会发生变化。这些选项以`-X`开头。

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
**如果未设置此选项，则初始大小将设置为分配给老年代和年轻代的大小之和**。可以使用-Xmn选项或-XX:NewSize选项设置年轻代堆的初始大小。
> 选项`-XX:InitalHeapSize`也可以用来设置初始堆大小。如果该选项出现在命令行的-Xms之后，那么初始堆大小将被设置为-XX:InitalHeapSize指定的值。

##### -Xmx size
指定内存分配池的最大大小（以字节为单位），单位为字节。**此值必须是1024的倍数并且大于2MB**。附加字母k或k表示千字节，m或m表示兆字节，g或g表示千兆字节。默认值是在运行时根据系统配置选择的。对于服务器部署，-Xms和-Xmx通常设置为相同的值。以下示例显示了如何使用各种单位将已分配内存的最大允许大小设置为80MB：
```
-Xmx83886080
-Xmx81920k
-Xmx80m
```
-Xmx选项等同于于`-XX:MaxHeapSize`。

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

##### -XX:+PrintCommandLineFlags
启用打印出现在命令行上符合人体工程学选择的JVM标志。了解JVM设置的人体工程学值很有用，例如堆空间大小和所选的垃圾收集器。默认情况下，此选项处于禁用状态并且不会打印标志。

##### -XX:-UseContainerSupport
虚拟机现在提供了自动容器检测支持，允许虚拟机确定在docker容器中运行的Java进程可用的内存量和处理器数量。它使用这些信息来分配系统资源。此支持仅在Linux x64平台上可用。如果支持，则此标志的默认值为true，并且默认情况下启用容器支持。可以使用-XX:-UseContainerSupport禁用它。

统一日志记录可帮助诊断与此支持相关的问题。

使用`-Xlog:os+container=trace`最大限度地记录容器信息。有关使用统一日志记录的说明，请参阅[使用JVM统一日志记录框架启用日志记录](https://docs.oracle.com/en/java/javase/11/tools/java.html#GUID-BE93ABDC-999C-4CB5-A88B-1994AAAC74D5)。

##### -XX:VMOptionsFile=filename
允许用户在文件中指定 VM 选项，例如：`java -XX:VMOptionsFile=/var/my_vm_options HelloWorld`。

这种方式相比直接在命令行上设置大量的选项参数要简洁的多。

### Java高级JIT编译器选项



### Java高级可维护性选项



### Java高级垃圾回收选项
> 有关JVM一些默认值（垃圾回收器、堆、编译器等）的说明：https://docs.oracle.com/en/java/javase/11/gctuning/ergonomics.html#GUID-DA88B6A6-AF89-4423-95A6-BBCBD9FAE781



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