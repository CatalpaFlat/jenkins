# 自定义过滤器实现接口需授权认证时响应json而不是security的跳转登录页面 #

> 需求：  
> 对于需要授权认证的接口，当被访问的时。
> - Spring Security 默认实现是：拦截 -> 跳转登录页面  
>
> 但当我们在实现某一些场景的时，不想要跳转登录页面（如app，小程序等），而是响应json，以此告诫调用者此接口需要授权认证才可获取资源信息。   

> 实现：  
> 简单描述OAuth2 的实现是：  
  AuthenticationServer ->认证->授权->token  
  ResourcesSever -> 校验token -> 发放资源  
>左思右想，既然security的授权认证，都得经过security自身的过滤器链，因此我们可以在过滤器链上动手脚。  
怎么动手脚呢？我们授权认证的依据其实是验证AccessToken，不管是jwt还是uuid类型的Token，我们都可进行验证，而且访问接口时，需要在请求头上附带Token信息，以此我们才可以获取其来进行验证。  
具体实现只有两步：
> 1. 在过滤器中添加自定义过滤器，以此过滤需认证的接口
> 2. 校验token的正确性

## 一、自定义过滤器 ##
```java


```
