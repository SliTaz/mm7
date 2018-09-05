-- Start of generated script for 211.94.133.105-ucmms-UCMMS (ucmms)
--  Jul-07-2011 at 14:01:59

DROP SPECIFIC PROCEDURE UCMMS.SQL110608094436900;

#SYNC 10;

SET SCHEMA UCMMS   ;

SET CURRENT PATH = "SYSIBM","SYSFUN","SYSPROC","SYSIBMADM","UCMMS";

CREATE PROCEDURE UCMMS.PRO_INSERT_MMSUSER
 (OUT R_CONTENTID DECIMAL(31, 0), 
  OUT R_USERS INTEGER, 
  OUT R_VALUE INTEGER
 ) 
  SPECIFIC UCMMS.SQL110608094436900
  LANGUAGE SQL
  NOT DETERMINISTIC
  CALLED ON NULL INPUT
  MODIFIES SQL DATA
  INHERIT SPECIAL REGISTERS
  begin
  
    declare v_contentid decimal(31) default 0;
    declare v_serviceid decimal(31) default 0;
	declare v_contentrow int default 1;
    declare v_contentname varchar(255) default '';
    declare v_contentpath varchar(100) default '';
    declare v_vasid varchar(20) default '';
    declare v_vaspid varchar(20) default '';
    declare v_sp_productid varchar(50) default ''; 
	
	declare not_found condition for sqlstate '02000';
	
    declare content_cursor cursor with hold
    for
    select
    c.contentid, 
    c.serviceid, 
    c.contentname, 
    c.contentpath, 
    s.vasid,
    s.vaspid,
    s.sp_productid
    from content_info c,
   (
	select distinct v.uniqueid,v.vasid,v.vaspid,r.sp_productid 
	from vasservice v,vasservice_reserve_info r
	where 1=1
	and v.servicecode = r.productid
	and v.feetype='1'
	and v.STATUS='2'
    ) s
    where 2=2
    and c.serviceid=s.uniqueid
    and c.contenttype='0'
    and c.status='4'
    and c.sendflag='3'
    and c.validstarttime<=current_timestamp
    and date(c.validstarttime)=current_date
	and c.contentid not in(select distinct contentid from user_send_temp)
    order by c.validstarttime asc
    fetch first 1 row only;
	
	declare continue handler for not_found  set v_contentrow=0;
	
	set r_contentid=0;
	set r_users=0;
	set r_value=1;


	open content_cursor;
	
		 fetch content_cursor into v_contentid,v_serviceid,v_contentname,v_contentpath,v_vasid,v_vaspid,v_sp_productid;
		 
		 if v_contentrow=0 then
		  	set r_contentid=0;
			set r_value=0;
		 else
		 	set r_contentid=v_contentid;
			set r_value=0;
		 end if;
		 
	close content_cursor;
	
	if(v_contentid>0 and r_value=0) then
	
		update content_info set sendflag='2',status='20',validendtime=current_timestamp where contentid=v_contentid;
		
		insert into user_send_temp(
		contentid,
		serviceid,
		vasid,
		vaspid,
		productid,
		usernumber,
		chargenumber,
		contentname,
		contentpath,
		sendaddress
		)
		select
		v_contentid,
		v_serviceid,
		v_vasid,
		v_vaspid,
		v_sp_productid,
		cellphonenumber,
		chargeparty, 
		v_contentname,
		v_contentpath,
		v_vasid
		from user_order
		where 3=3 
		and status=0
		and serviceuniqueid= v_serviceid;
		
		get diagnostics r_users = row_count;
		
		if(r_users=0) then
		    
			update content_info set sendflag='1',status='30',validendtime=current_timestamp where contentid=v_contentid; 
			
		else
			
			begin
			    
				update content_info set sendflag='2',status='10',validendtime=current_timestamp where contentid=v_contentid;
			    commit;
				
			end;
		
		end if;

	else
		
		return;
	
	end if;


end;

#SYNC 20;



-- End of generated script for 211.94.133.105-ucmms-UCMMS (ucmms)