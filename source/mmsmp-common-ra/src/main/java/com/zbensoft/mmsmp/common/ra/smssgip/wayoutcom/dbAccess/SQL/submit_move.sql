CREATE PROCEDURE `submit_move`()
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

select value into Smove from smsparameter where name = 'submit_bak';
select value into tries from smsparameter where name = 'submit_retries';

insert into smssubmitok select preCommand,pre_seq1,pre_seq2,pre_seq3 from smssubmitresend where (TIMESTAMPDIFF(minute,convertTime, nowtime)>=Smove and resp = 'Y');
set @inst = ROW_COUNT();
delete from smssubmitresend where (TIMESTAMPDIFF(minute,convertTime, nowtime)>=Smove and resp = 'Y');
set @updt = ROW_COUNT();

IF updt!=inst THEN rollback;
ELSE COMMIT;
END IF;

insert into smssubmitnok select preCommand,pre_seq1,pre_seq2,pre_seq3 from smssubmitresend where (retries >= tries and resp = 'N');
set @inst = ROW_COUNT();
delete from smssubmitresend where (retries >= tries and resp = 'N');
set @updt = ROW_COUNT();

IF updt!=inst THEN rollback;
ELSE COMMIT;
END IF;

end;
