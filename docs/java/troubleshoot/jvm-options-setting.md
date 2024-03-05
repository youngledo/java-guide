# 如何正确的设置JVM参数

随着服务容器化部署，特别是Docker、Kubernetes横行之下，如何正确的部署服务显得尤为重要，对于Java服务来说部署时设置JVM参数是个常见的事情。但大多数人并不清楚或并不了解在容器中如何正确的设置JVM参数，比如这样的：
![jvm-dockerfile.png](assets/jvm-dockerfile.png)
这种方式思路肯定是可行的，但是里面有些坑， 此处不详细描述请看[passing-java-opts-to-spring-boot-application-through-docker-compose](https://stackoverflow.com/questions/53785577/passing-java-opts-to-spring-boot-application-through-docker-compose)。简单来说就是，Dockerfile注入的方式，在某些shell启动的场景会存在无法接收到“终止信号”（sigterm）。

#### 那么推荐的做法是什么呢？

这一点其实在Oracle官网有说明，[environment-variables-and-system-properties](https://docs.oracle.com/en/java/javase/11/troubleshoot/environment-variables-and-system-properties.html#GUID-A91E7E21-2E91-48C4-89A4-836A7C0EE93B)，但对于Java 9之后更推荐使用[JDK_JAVA_OPTIONS](https://docs.oracle.com/en/java/javase/11/tools/java.html#GUID-3B1CE181-CD30-4178-9602-230B800D4FAE__USINGTHEJDK_JAVA_OPTIONSLAUNCHERENV-F3C0E3BA)。另外，Google的[JIB](https://github.com/GoogleContainerTools/jib)也对于VM参数的设置做了说明：[how-do-i-set-parameters-for-my-image-at-runtime](https://github.com/GoogleContainerTools/jib/blob/master/docs/faq.md#how-do-i-set-parameters-for-my-image-at-runtime)：
![jvm-options-k8s.png](assets/jvm-options-k8s.png)
Java 9之前用`JAVA_TOOL_OPTIONS`，之后用`JDK_JAVA_OPTIONS`。当然这也仅限Oracle官方的HotSpot VM，如IBM则叫IBM_JAVA_OPTIONS。
注意：至于JAVA_OPTS其实不是JVM所识别的参数，而是一些三方应用定义的，比如Tomcat。而`JAVA_TOOL_OPTIONS`是标准的，所有虚拟机都能识别和应用的。

##### 区别
- JDK_JAVA_OPTIONS：环境变量仅作用于java命令启用的程序（java -jar example.jar）。
- JAVA_TOOL_OPTIONS：环境变量除了对java命令生效，也对javac、jar等命令生效。

详细解释：[what-is-the-difference-between-jdk-java-options-and-java-tool-options-when-using](https://stackoverflow.com/questions/52986487/what-is-the-difference-between-jdk-java-options-and-java-tool-options-when-using)。