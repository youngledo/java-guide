# jstatd
> [Java Statistical Daemon](https://docs.oracle.com/en/java/javase/11/tools/jstatd.html#GUID-FA737806-75CD-4EE7-A087-1CC4A5441870__BABGDCJC)：用来监视Java HotSpot虚拟机的创建和终止。
>
> jstatd命令是一个RMI服务器应用程序，用于监视Java HotSpot VMs的创建和终止，并提供一个接口来启用远程监视工具`jstat`和`jps`，以连接到在本地主机上运行的JVM并收集有关JVM进程的信息。
> 
> `jstatd`服务器需要本地主机上的RMI注册表。`jstatd`服务器尝试连接到默认端口或使用`-p`端口选项指定的端口上的RMI注册表。如果找不到RMI注册表，则会在`jstatd`应用程序中创建一个注册表，该注册表绑定到`-p`端口选项指示的端口，或者在省略`-p`端口选项时绑定到默认RMI注册表端口。您可以通过指定`-nr`选项来停止创建内部RMI注册表。

### jstatd命令的选项

#### -nr
当找不到现有的RMI注册表时，此选项不会尝试在jstatd进程中创建内部RMI注册表。

#### -p port
此选项设置RMI注册表的端口号，如果未指定-nr选项，则在找不到RMI注册表时创建端口号。

#### -n rminame
此选项设置远程RMI对象在RMI注册表中绑定到的名称。默认名称为`JStatRemoteHost`。如果多个`jstatd`服务器在同一主机上启动，则可以通过指定此选项使每个服务器的导出RMI对象的名称唯一。但是，这样做需要在监视客户端的`hostid`和`vmid`字符串中包含唯一的服务器名称。

#### -Joption
此选项将Java选项传递给JVM，其中该选项是Java应用程序启动器的参考页面上描述的选项之一。例如，`-J-Xms48m`将启动内存设置为48MB。请参阅java。

### 安全
jstatd 服务器只能监视它拥有适当的本机访问权限的JVM。因此， jstatd 进程必须使用与目标JVM相同的用户凭证运行。某些用户凭据（如Oracle Solaris、Linux和macOS操作系统中的root用户）有权访问系统上任何JVM导出的检测。使用这种凭证运行的 jstatd 进程可以监视系统上的任何JVM，但会引入额外的安全问题。

jstatd服务器不提供任何远程客户端的身份验证。因此，运行jstatd服务器进程会将jstatd进程具有访问权限的所有JVM的检测导出暴露给网络上的任何用户。这种暴露在您的环境中可能是不可取的，因此，在启动jstatd过程之前，应考虑本地安全策略，特别是在生产环境或不安全的网络中。

当没有安装其他安全管理器时，jstatd服务器会安装`RMISecurityPolicy`的实例，因此需要指定一个安全策略文件。策略文件必须符合默认策略实现和策略文件语法。

如果您的安全问题无法通过自定义的策略文件来解决，那么最安全的操作是不运行`jstatd`服务器，而是在本地使用`jstat`和`jps`工具。但是，当使用jps获取插入指令的JVM的列表时，该列表将不包括在docker容器中运行的任何JVM。

### 示例
以下是 jstatd 命令的示例。 jstatd 脚本在后台自动启动服务器。

#### 内部RMI注册表
此示例显示如何使用内部RMI注册表启动 jstatd 会话。此示例假定没有其他服务器绑定到默认RMI注册表端口（端口 1099 ）。
```shell
jstatd -J-Djava.security.policy=all.policy
```

#### 外部RMI注册表
此示例使用外部RMI注册表启动 jstatd 会话。
```shell
rmiregistry&
jstatd -J-Djava.security.policy=all.policy
```

此示例在端口 2020 上启动与外部RMI注册表服务器的 jstatd 会话。
```shell
jrmiregistry 2020&
jstatd -J-Djava.security.policy=all.policy -p 2020
```

此示例使用绑定到 AlternateJstatdServerName 的端口2020上的外部RMI注册表启动 jstatd 会话。
```shell
rmiregistry 2020&
jstatd -J-Djava.security.policy=all.policy -p 2020 \
   -n AlternateJstatdServerName
```

#### 停止创建进程内RMI注册表
本例启动一个 jstatd 会话，当找不到RMI注册表时，该会话不会创建RMI注册表。本例假设RMI注册表已经在运行。如果RMI注册表未运行，则会显示错误消息。
```shell
jstatd -J-Djava.security.policy=all.policy -nr
```

#### 启用RMI日志记录
```shell
jstatd -J-Djava.security.policy=all.policy \
    -J-Djava.rmi.server.logCalls=true
```