CREATE TABLE `sp_userinfo` (
  `username` varchar(20) NOT NULL,
  `password` varchar(10) default NULL,
  `cellphonenumber` varchar(20) default NULL,
  `name` varchar(20) default NULL,
  `createtime` varchar(20) default NULL,
  `status` char(1) default NULL,
  PRIMARY KEY  (`username`),
  UNIQUE KEY `cellphonenumber` (`cellphonenumber`)
) ENGINE=MyISAM DEFAULT CHARSET=gbk
