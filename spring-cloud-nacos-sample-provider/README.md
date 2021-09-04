## Spring Cloud Alibaba Nacos Discovery:基于Spring Cloud规范快速接入Nacos,实现服务注册与发现功能
### comm：接口定义类的依赖
### spring-cloud-starter：Spring Cloud核心包
### spring-cloud-starter-dubbo：引入Spring Cloud Alibaba Dubbo
### spring-cloud-alibaba-nacos-discovery：基于Nacos的服务注册与发现
### spring-cloud-context：spring-cloud-starter传递依赖的spring-cloud-context版本存在兼容问题，会导致ClassNotFoundException，所以通过exclusion排除了依赖，并且引入了2.1.1.RELEASE版本来解决
