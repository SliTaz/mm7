CREATE PROCEDURE `submit_resend`(OUT comm VARBINARY(200))
    NOT DETERMINISTIC
    SQL SECURITY DEFINER
    COMMENT ''
BEGIN
declare ssend int default 1;
declare interv int default 1;
DECLARE nowtime datetime DEFAULT now();

select value into interv from smsparameter where name = 'submit_interval';
select value into ssend from smsparameter where name = 'submit_resend';
select count(*), Command into comm from smssubmit where resp = 'N' and ((TIMESTAMPDIFF(minute,convertTime, nowtime)>=ssend and retries = 0) or (TIMESTAMPDIFF(minute,convertTime, nowtime)>=interv and retries != 0));
update smssubmit set retries = retries+1 where resp = 'N' and ((TIMESTAMPDIFF(minute,convertTime, nowtime)>=ssend and retries = 0) or (TIMESTAMPDIFF(minute,convertTime, nowtime)>=interv and retries != 0));
END;
