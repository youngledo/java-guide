# Visual VM
[Visual VM](https://visualvm.github.io/documentation.html)是一款功能强大的Java应用程序性能分析和故障排查工具，它整合了多种JDK命令行工具的功能，并提供了一个直观的图形化界面。以下是一些主要功能：
1. 系统概览：
    - 实时监控：展示所有本地或远程运行的Java进程的基本信息，包括CPU使用率、内存占用（堆、非堆）、线程数量及状态等。 
2. 内存分析：
   - 内存消耗图表：实时显示Java堆内存的分配和垃圾回收情况。
   - 堆转储分析：抓取并分析Java堆内存转储（Heap Dump），识别内存泄漏、对象分布和大小统计等。
   - 监控类加载器：查看已加载类的数量及其占用空间。
3. 线程分析：
   - 线程视图：查看和跟踪所有活动线程的状态，查找死锁或其他线程同步问题。
   - 线程转储：生成线程转储文件，用于离线分析线程堆栈信息。
4. 性能剖析：
   - CPU采样：通过抽样方法来分析CPU时间的消耗，找出热点代码。
   - 内存剖析：监测内存分配情况，揭示潜在的内存使用效率低下之处。
   - 采样器和Profiler：支持更深入的CPU和内存分析，包括分配剖析、CPU快照等。
5. MBeans浏览器：
   - 查看和操作通过JMX（Java Management Extensions）暴露出来的MBeans属性和操作，进行系统管理和配置。
6. 插件扩展：
   - Visual VM支持安装各种插件以增强功能，例如Visual GC插件可以可视化垃圾收集器的行为。
7. 远程监控：
   - 支持通过JMX连接远程Java应用，允许用户在不访问服务器的情况下监控和调试远程服务。
8. 历史数据记录和比较：
   - 可以保存和回放监控的数据，便于对不同时间段的性能数据进行对比分析。
9. 报告导出：
   - 可将分析结果导出为报告格式，方便分享和存档。

总之，Visual VM是一个全能型的Java应用性能管理工具，能够帮助开发者和运维人员深入了解应用内部的工作机制，及时发现并解决性能瓶颈等问题。另外Visual VM还支持插件，详情请参考[https://visualvm.github.io/plugins.html](https://visualvm.github.io/plugins.html)。


## 附录
### Visual VM监控kubernates中的Java应用
1. Deployment.yml中配置JMX远程连接
   ```yaml
   containers:
     - name: upgrade-java
       ports:
         # 配置JMX端口
         - name: jmx
           containerPort: 1099
           protocol: TCP
       env:
           - name: JDK_JAVA_OPTIONS
             # 配置JMX远程连接
             value: >-
               -Djava.rmi.server.hostname=localhost
               -Dcom.sun.management.jmxremote.port=1099
               -Dcom.sun.management.jmxremote.ssl=false
               -Dcom.sun.management.jmxremote.authenticate=false
               -Dcom.sun.management.jmxremote.rmi.port=1099
   ```
2. 端口转发

   ![kube-port-forward-lens.png](assets/visual-vm/kube-port-forward-lens.png)
3. Visual VM自动显示了该Java进程

   ![kube-visual-vm.png](assets/visual-vm/kube-visual-vm.png)