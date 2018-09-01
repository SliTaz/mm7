CREATE PROCEDURE `report_move`()
    NOT DETERMINISTIC
    SQL SECURITY DEFINER
    COMMENT ''
begin
DECLARE nowtime datetime DEFAULT now();
declare inst int default 0;
declare updt int default 0;
declare Rmove int default 1;
declare tries int default 1;
START TRANSACTION;

select value into Rmove from smsparameter where name = 'report_bak';
select value into tries from smsparameter where name = 'report_retries';
insert into smsreportok select Command,cseq1,cseq2,cseq3 from smsreportresend where (TIMESTAMPDIFF(minute,recvTime, nowtime)>=Rmove and resp = 'Y');
set @inst = ROW_COUNT();
delete from smsreportresend where (TIMESTAMPDIFF(minute,recvTime, nowtime)>=Rmove and resp = 'Y');
set @updt = ROW_COUNT();

IF updt!=inst THEN rollback;
ELSE COMMIT;
END IF;

insert into smsreportnok select Command,cseq1,cseq2,cseq3 from smsreportresend where (retries >= tries and resp = 'N');
set @inst = ROW_COUNT();
delete from smsreportresend where (retries >= tries and resp = 'N');
set @updt = ROW_COUNT();

IF updt!=inst THEN rollback;
ELSE COMMIT;
END IF;
end;
