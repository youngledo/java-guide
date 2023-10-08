# jstack
> [JVM Stack Trace](https://docs.oracle.com/en/java/javase/11/tools/jstack.html#GUID-721096FC-237B-473C-A461-DBBBB79E4F6A)，打印指定Java进程的堆栈跟踪。对于核心文件，请使用[jhsdb](java/tools/cli/jhsdb.md)。
> 
> 可用于定位线程长时间停顿的原因，如线程间死锁、死循环、请求外部资源导致的长时间等待等问题。
> 
> 在线程堆栈中，需要留意下面几种状态：
> - **Deadlock**，死锁
> - **Waiting on condition**，等待资源条件
> - **Waiting on monitor entry**，等待获取监视器
> - **BLOCKED**，阻塞
> - RUNNABLE，执行中
> - SUSPENDED，暂停
> - TIMED_WAITING，超时等待
> - PARKED，停止

### 使用说明

终端输入`jstack`即可查看帮助手册：

![jstack.png](assets/jstack.png)