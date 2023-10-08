# jstat
> [JVM Statistics Monitor Tool](https://docs.oracle.com/en/java/javase/11/tools/jstat.html#GUID-5F72A7F9-5D5A-4486-8201-E1D1BA8ACCB5)：用于监视虚拟机各种运行状态的命令行工具。
> 
> 它可以显示本地或者远程虚拟机进程中的类装载、内存、垃圾回收、JIT编译器（即时编译器）等运行数据。 在没有图形化界面时，它将是运行期定位虚拟机性能问题的首选工具。

## 使用说明
![jstat-help.png](assets/jstat-help.png)

### 选项说明
##### 1. class选项：显示有关类加载器行为的统计信息。
- Loaded: 加载的类数。
- Bytes: 已加载类的KB数。
- Unloaded: 已卸载的类数。
- Bytes: 已卸载的KB数。
- Time: 执行类加载和卸载操作所花费的时间。
```bash
# 1. 输出简单的类加载信息：jstat -class <vmid>
$ jstat -class 6272
Loaded  Bytes  Unloaded  Bytes     Time
   989  2281.3        0     0.0       0.16
   
# 2. 每隔1000毫秒输出一次：jstat -class <vmid> [<interval>]
$ jstat -class 6272 1000
Loaded  Bytes  Unloaded  Bytes     Time   
   989  2281.3        0     0.0       0.16
   989  2281.3        0     0.0       0.16
   989  2281.3        0     0.0       0.16
   989  2281.3        0     0.0       0.16
   
# 3. 每隔300毫秒并输出2次：jstat -class <vmid> [<interval> [<count]]
$ jstat -class 6272 300 2
Loaded  Bytes  Unloaded  Bytes     Time   
   989  2281.3        0     0.0       0.16
   989  2281.3        0     0.0       0.16

# 4. 输出类加载器的运行时长：jstat -class [-t] <vmid>
$ jstat -class -t 6272
Timestamp Loaded  Bytes  Unloaded  Bytes     Time   
   1449.3    989  2281.3        0     0.0    0.16

# 5. 输出指定行数的表头信息：jstat -class [-t] [-h<lines>] <vmid> [<interval> [<count]]
$ jstat -class -t -h2 6272 1000 5
Timestamp       Loaded  Bytes  Unloaded  Bytes     Time   
         1869.3    989  2281.3        0     0.0       0.16
         1870.4    989  2281.3        0     0.0       0.16
Timestamp       Loaded  Bytes  Unloaded  Bytes     Time   
         1871.4    989  2281.3        0     0.0       0.16
         1872.4    989  2281.3        0     0.0       0.16
Timestamp       Loaded  Bytes  Unloaded  Bytes     Time   
         1873.4    989  2281.3        0     0.0       0.16
```

##### 2. compiler选项：显示有关 Java HotSpot VM 即时编译器行为的统计信息。
-compiler：输出即时编译器编译过的方法、耗时等统计信息。
- Compiled: 已执行的编译任务数。
- Failed: 失败的编译任务数。
- Invalid: 无效的编译任务数。
- Time: 执行编译任务所花费的时间。
- FailedType: 最后一次编译失败的编译类型。
- FailedMethod: 最后一次编译失败的类名和方法。
```bash
# jstat -compiler <vmid>
$ jstat -compiler 6272
Compiled Failed Invalid   Time   FailedType FailedMethod
     248      0       0     0.13          0             
```

-printcompilation：显示Java HotSpot VM编译方法统计信息。
- Compiled: 最近编译的方法所执行的编译任务数。
- Size: 最近编译的方法的字节代码数。
- Type: 最近编译的方法的编译类型。
- Method: 类名和方法名，用于标识最近编译的方法。类名使用斜线（/）而不是点（.）作为名称空间分隔符。方法名称是指定类中的方法。这两个字段的格式与HotSpot`-XX:+PrintCompilation`选项一致。
```bash
# jstat -printcompilation <vmid>
$ jstat -printcompilation 6272
Compiled  Size  Type Method
     248     26    1 java/lang/String length

```

##### 3. gc选项：显示有关垃圾收集堆行为的统计信息
-gc：垃圾回收堆统计信息。

-gccapacity：内存池生成和空间容量。

-gccause：此选项显示与`-gcutil`功能一样，但包括上次垃圾回收事件和当前垃圾回收事件的原因。除了为`-gcutil`列出的列之外，此选项还添加以下列：

-gcnew：新生代统计数据。

-gcnewcapacity：新生代空间大小统计。

-gcold：老年代统计数据。

-gcoldcapacity：老年代大小统计。

-gcmetacapacity：元空间大小统计。

-gcutil：垃圾回收统计信息摘要。

### 图形化工具
> [VisualGC](https://www.oracle.com/java/technologies/visual-garbage-collection-monitoring-tool.html)与jstat工具相关，提供垃圾回收、编译器和类加载器的图形化界面。
>
> VisualGC 工具不包含在JDK版本中，但可以从[jvmstat](https://www.oracle.com/java/technologies/jvmstat.html)技术页面单独下载。

