CREATE PROCEDURE `smgp_deliver_move`()
    NOT DETERMINISTIC
    SQL SECURITY DEFINER
    COMMENT ''
begin
 DECLARE nowtime datetime DEFAULT now();
 declare inst int default 0;
 declare updt int default 0;
 declare Dmove int default 1;
 declare tries int default 1;

     START TRANSACTION;
     select value into Dmove from smgp_parameter where name = 'deliver_bak';
     select value into tries from smgp_parameter where name = 'deliver_retries';
     insert into smgp_deliverok (seq) select seq from smgp_delivertimeout where (TIMESTAMPDIFF(minute,recvTime, nowtime)>=Dmove and resp = 'Y');
     set @inst = ROW_COUNT();
     delete from smgp_delivertimeout where (TIMESTAMPDIFF(minute,recvTime, nowtime)>=Dmove and resp = 'Y');
     set @updt = ROW_COUNT();
     IF updt!=inst
        THEN rollback;
        ELSE COMMIT;
     END IF;
     
      insert into smgp_delivernok (seq) select seq from smgp_delivertimeout where (retries >= tries and resp = 'N');
      set @inst = ROW_COUNT();
      delete from smgp_delivertimeout where (retries >= tries and resp = 'N');
      set @updt = ROW_COUNT();
     IF updt!=inst
        THEN rollback;
        ELSE COMMIT;
     END IF;
end;
