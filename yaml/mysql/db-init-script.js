// Used by the MySQL Shell to initialize and prepare a node to become a member of the InnoDB Cluster
var data = {host: 'localhost', port:3306, user:'root', password: 'icsd15201'};
var options = {interactive: false};
dba.configureInstance(data,options);
