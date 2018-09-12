set Axis_Lib=D:\apache-tomcat-7.0.61\webapps\mmsmpspsimulator\WEB-INF\lib
set Java_Cmd=java -Djava.ext.dirs=%Axis_Lib%
%Java_Cmd%  org.apache.axis.client.AdminClient -lhttp://127.0.0.1/mmsmpspsimulator/services/SyncNotifySP deploy.wsdd