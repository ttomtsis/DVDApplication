:: bat script that will delete all the cluster resources
:: created by the setup.bat script including
:: secret, config map and pv's.
:: This script also checks if the pv's have been successfully deleted after their
:: respective pvc's have been deleted and if the pv's still exist
:: it deletes them
echo WARNING: Requires Python3 to run
python3 delete.py