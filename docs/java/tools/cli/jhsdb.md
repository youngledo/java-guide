# jhsdb
> [Java HotSpot Serviceability Agent Debugger](https://docs.oracle.com/en/java/javase/11/tools/jhsdb.html#GUID-0345CAEB-71CE-4D71-97FE-AA53A4AB028E)是一个命令行工具，它提供了在HotSpot虚拟机上进行调试的能力，它包含了多种工具，可以用来查看和修改正在运行的Java程序的状态，例如：查看线程栈信息、查看堆中对象的信息、执行垃圾回收等。主要用于HotSpot虚拟机开发者和高级用户进行虚拟机级别的调试。
>
> **注意**：将`jhsdb`工具附加到实时进程将导致进程挂起，并且当调试器分离时进程可能会崩溃。

### jhsdb clhsdb
Starts the interactive command-line debugger.

### jhsdb debugd
Starts the remote debug server.

### jhsdb hsdb
Starts the interactive GUI debugger.

### jhsdb jstack
Prints stack and locks information.

### jhsdb jcmd
打印堆信息。

![jhsdb-jcmd.png](assets%2Fjhsdb-jcmd.png)

### jhsdb jinfo
打印 JVM 的基本信息。

### jhsdb jsnap
Prints performance counter information.