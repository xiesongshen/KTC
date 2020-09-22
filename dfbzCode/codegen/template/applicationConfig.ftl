<#import "function.ftl" as func>
<#assign system=vars.system>
server:
  port: 9001
spring: 
  application:  
    name: ktc-${system} #指定服务名
  datasource:  
    driverClassName: com.mysql.jdbc.Driver
    url: jdbc:mysql://192.168.60.130:3307/ktc_${system}?characterEncoding=UTF8
    username: root
    password: admin
  jpa:
    properties:
      hibernate:
        format_sql: true	# 格式化sql
        show_sql: true		# 显示sql
