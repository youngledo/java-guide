# 概述
> Java作为最流行的编程语言（之一），其应用性能诊断、监控一直受到业界广泛关注。
> 当然造成应用出现性能问题的因素非常多，例如线程控制、磁盘读写、数据库访问、网络I/O、垃圾回收等。
> 
> 要想定位这些问题，优秀的诊断工具必不可少。比如大家熟知的[Grafana](https://grafana.com/zh-cn/grafana/?tech=target&plcmt=footer-banner&aud=china#grafana-versions)、[prometheus](https://prometheus.io/)，不过这些工具可能更多的是从运维层面展示，而实际我们需要从开发层面去发现问题、分析问题、解决问题。
> 1. 无监控、不调优。
> 2. 使用数据说明问题、使用知识分析问题、使用工具处理问题。

在我们刚学习java的时候，大家最先接触的是`java`、`javac`这两个命令，实际上JDK还为我们添加了很多其它非常有用的工具，利用这些工具帮助开发人员很好的解决Java应用程序的一些疑难杂症。
- Mac系统下：
![java-bin-mac.png](assets/java-bin-mac.png)
- Ubuntu系统下：
![java-bin-ubuntu.png](assets/java-bin-ubuntu.png)
- Windows系统下：
![java-bin-windows.png](assets/java-bin-windows.png)

## JDK Tool Specifications
> 需要注意的是每个JDK版本工具可能不太一样，就算不同版本都有相同的工具其可选参数也可能不一样，更不用说不同发行版的JDK更是如此，请参考：[Java Platform, Standard Edition Documentation](https://docs.oracle.com/en/java/javase/index.html)。

```plantuml
@startmindmap
+[#17ADF1] Java诊断工具
++ 命令行
+++[#FFC1C1] jps（查看Java进程）
+++[#90EE90] jstat（查看JVM统计信息）
+++[#EED2EE] jinfo（实时查看和修改JVM配置参数）
+++[#FFA500] jmap（导出内存映像文件、内存使用情况）
+++[#FFD39B] jhat（堆分析工具）
+++[#EE799F] jstack（线程快照）
+++[#EEAEEE] jcmd
+++[#EEBEEE] java
++ 图形化
+++[#FFC1C1] jconsole
+++[#90EE90] Visual VM
+++[#EED2EE] JDK Mission Control（生产时分析和诊断工具）
+++[#FFA500] Java Flight Recorder（分析和事件收集框架）
+++[#FFD39B] MAT（Eclipse Memory Analyzer Tool）
+++[#EE799F] Visual GC
-- 三方工具
---[#1DBAAF] Arthas（Java应用诊断利器）
---[#9C2B1B] JVM Sandbox（一种JVM的非侵入式运行期AOP解决方案）
---[#FFEE2E] BTrace（用于Java平台的安全、动态跟踪工具）
---[#418CDA] NetBeans Profiler（监视应用程序的线程状态、CPU 性能和内存使用情况）
---[#EC6924] YourKit
---[#0993E2] JProfiler
---[#C0FF3E] MAT（Eclipse Memory Analyzer Tool）
--- 自定义（除了以上工具以外，你也可以自定义自己的诊断工具）
@endmindmap
```

> 参考网络文章：https://learn.lianglianglee.com/%E4%B8%93%E6%A0%8F/JVM%20%E6%A0%B8%E5%BF%83%E6%8A%80%E6%9C%AF%2032%20%E8%AE%B2%EF%BC%88%E5%AE%8C%EF%BC%89