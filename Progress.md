#  Progress Log

## 20th July,2023 
 - Created Repo for online marketplace 
 - Created Order Service infra 
 - Created Product Service infra 

## 7th Sep,2023
- Web Client integration b/w Order Service & Inventory Service 
- Created Discovery Service infra 

## 18th Sep,2023
- Checking the behaviour of discovery client (Eureka)
- Adding api-gateway project 
  - http://localhost:8080/eureka/web
  - http://localhost:8080/api/product

## 20th Sep,2023
- Adding actuator dependency
- Api-gateway project 
  - http://localhost:8080/actuator/gateway/routes
  - http://localhost:8080/actuator/gateway/globalfilters
  - http://localhost:8080/actuator/gateway/routefilters

## 16th Oct,2023
- Adding keycloak
- docker run -p 8081:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:22.0.4 start-dev
- Still to be completed

## 18th Oct,2023
- Implement Observability Stack 
- Ref - https://programmingtechie.com/2023/09/09/spring-boot3-observability-grafana-stack/
- Configured Loki 
- Configured Tempo
- Configured Prometheus 

## 20th Oct,2023
- Adding Loggers for Gateway , RequestResponseLoggingFilter & GlobalFilter
- Adding Loggers for Services , CommonsRequestLoggingFilter , still need to tuned
- Update puml jar , https://github.com/plantuml-stdlib/C4-PlantUML