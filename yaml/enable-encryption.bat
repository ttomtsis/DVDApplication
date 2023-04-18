:: This script is only to be used for reference and has not been tested
:: simply contains the base commands to be used in order to apply encryption of resources
:: in a minikube cluster. Note that the process has been somewhat buggy so far and
:: it is uncertain if minikube actually encrypts the resources

:: Approach 1: Uncertain if it works due to a minikube bug
minikube cp ./encryption.yaml /encryption.yaml
minikube start --extra-config=apiserver.encryption-provider-config=/encryption.yaml

:: Approach 2: Probably works, the config filepath actually shows up in the 
:: apiserver config file
minikube mount .:/var/lib/minikube/certs/hack
minikube start --extra-config=apiserver.encryption-provider-config=/var/lib/minikube/certs/hack/encryption.yaml

