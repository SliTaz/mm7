CREATE PROCEDURE `cngp_submitresend_move`()
    NOT DETERMINISTIC
    SQL SECURITY DEFINER
    COMMENT ''
begin
DECLARE nowtime datetime DEFAULT now();
declare inst int default 0;
declare updt int default 0;
declare Smove int default 1;
declare tries int default 1;

START TRANSACTION;
select value into Smove from cngp_parameter where name = 'submit_bak';
select value into tries from cngp_parameter where name = 'submit_retries';
insert into cngp_submitresendok (seq) select seq from cngp_submitresend where (TIMESTAMPDIFF(minute,convertTime, nowtime)>=Smove and resp = 'Y');
set @inst = ROW_COUNT();
delete from cngp_submitresend where (TIMESTAMPDIFF(minute,convertTime, nowtime)>=Smove and resp = 'Y');

set @updt = ROW_COUNT();
IF updt!=inst
   THEN rollback;
   ELSE COMMIT;
END IF;
insert into cngp_submitresendnok (seq) select seq from cngp_submitresend where (retries >= tries and resp = 'N');
set @inst = ROW_COUNT();
delete from cngp_submitresend where (retries >= tries and resp = 'N');
set @updt = ROW_COUNT();
IF updt!=inst
   THEN rollback;
   ELSE COMMIT;
END IF;
end;
