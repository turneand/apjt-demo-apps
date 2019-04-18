# apjt-istio-demo
Sample application demonstrating a small Istio application running on a Windows Development machine with Docker For Desktop installed.
This was primarily done to document for personal reasons as trying to investigate |https://istio.io/|Istio|.

The steps below have been run successfully on a Windows 10 machine running 1809, with an i5 2.5GHz and 8GB RAM.

## Pre-requisites

### Install docker/kubernetes
- Install docker locally - https://docs.docker.com/docker-for-windows/install/
- Enable kubernetes - https://docs.docker.com/docker-for-windows/#kubernetes

### Install Istio
- Download Istio - https://istio.io/docs/setup/kubernetes/download/ (this version was used: https://github.com/istio/istio/releases/download/1.1.3/istio-1.1.3-win.zip)
- Extract Istio zip to a permanent location (e.g. C:\Program Files\Istio\istio-1.1.3)
- Create an ISTIO_HOME system environment variable pointing at the extracted location (e.g. C:\Program Files\Istio\istio-1.1.3)
- Update system Path variable to include %ISTIO_HOME%\bin
- Set the docker resources as below
-- CPUs: 2
-- Memory: 4096 MB
-- Swap: 4096MB
- Run the following command to install istio (taken and simplified from https://istio.io/docs/setup/kubernetes/install/kubernetes/), and the "strict mutual TLS" demo profile
```
kubectl apply -f "%ISTIO_HOME%\install\kubernetes\helm\istio-init\files"
kubectl apply -f "%ISTIO_HOME%\install\kubernetes\istio-demo-auth.yaml"
```
- Run some checks (note, that it can take some time before everything is marked as "Running" or "Completed"
```
kubectl get svc,pods -n istio-system
```



## Install demo application
See https://istio.io/docs/examples/bookinfo/ for the details

- Create a new namespace for the demo application, configuring it to use istio
```
kubectl create namespace istio-bookinfo-demo
kubectl label namespace istio-bookinfo-demo istio-injection=enabled
```
- Deploy the application, gateway and destination rules
```
kubectl apply -n istio-bookinfo-demo -f "%ISTIO_HOME%\samples\bookinfo\platform\kube\bookinfo.yaml"
kubectl apply -n istio-bookinfo-demo -f "%ISTIO_HOME%\samples\bookinfo\networking\bookinfo-gateway.yaml"
kubectl apply -n istio-bookinfo-demo -f "%ISTIO_HOME%\samples\bookinfo\networking\destination-rule-all-mtls.yaml"
```
- Check the status
```
kubectl get svc,pods,gateway,destinationrules -n istio-bookinfo-demo
```
- Additional check to ensure the services are accessible internally within the cluster
kubectl get pod -n istio-bookinfo-demo -l app=ratings -o jsonpath={.items[0].metadata.name}
kubectl exec -n istio-bookinfo-demo -it <RATINGS-POD-ID> -- curl productpage:9080/productpage
```
- As we're using docker for desktop on a windows machine, we can run the following commands to get the hostname (will be localhost) and port for the istio ingress (see https://istio.io/docs/tasks/traffic-management/ingress/#determining-the-ingress-ip-and-ports)
```
kubectl -n istio-system get service istio-ingressgateway -o jsonpath="{.status.loadBalancer.ingress[0].hostname}"
kubectl -n istio-system get service istio-ingressgateway -o jsonpath="{.spec.ports[?(@.name=='http2')].port}"
```
- Now test using a browser, navigate to http://<HOSTNAME>:<PORT>/productpage, which for me was http://localhost:80/productpage



## Test prometheus
See https://istio.io/docs/tasks/telemetry/metrics/querying-metrics/ for the details

- Setup a quick port forward
```
kubectl -n istio-system port-forward service/prometheus 9090:9090
```
- Now test using a browser, navigate to http://localhost:9090/graph



## Test grafana
See https://istio.io/docs/tasks/telemetry/metrics/using-istio-dashboard/ for the details

- Setup a quick port forward
```
kubectl -n istio-system port-forward service/grafana 3000:3000
```
- Now test using a browser, navigate to http://localhost:3000/dashboard/db/istio-mesh-dashboard



## Test jaeger
Following instructions from https://istio.io/docs/tasks/telemetry/distributed-tracing/jaeger/
- Setup a quick port forward
```
kubectl -n istio-system port-forward service/tracing 16686:80
```
- Now test using a browser, navigate to http://localhost:16686



## Uninstall Istio and sample apps
Once finished, run the following to remove all Istio installed items
```
kubectl delete ns istio-bookinfo-demo
kubectl delete ns istio-system
```
