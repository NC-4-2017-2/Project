create or replace TRIGGER insert_update_objects_to_temp
    AFTER INSERT OR UPDATE ON OBJECTS
    FOR EACH ROW
DECLARE
    v_object_id NUMBER(20);
    v_obj_type_id NUMBER(20);
    v_version NUMBER(20);
BEGIN
    v_object_id := :NEW.OBJECT_ID;
    v_obj_type_id := :NEW.OBJECT_TYPE_ID;
    v_version := :NEW.OBJECT_VERSION;

    MERGE INTO OBJECT_VERSION_TEMP temp1
    USING Dual
    ON (temp1.OBJECT_ID = v_object_id AND
    temp1.OBJ_TYPE_ID = v_obj_type_id)
    WHEN MATCHED
    THEN UPDATE SET temp1.VERSION = v_version
    WHEN NOT MATCHED
    THEN INSERT VALUES(v_object_id, v_obj_type_id, v_version);
END;
--------------------------------------------------------------
create or replace TRIGGER update_attributes
AFTER UPDATE ON ATTRIBUTES
FOR EACH ROW

DECLARE
  v_object_id NUMBER(20);
BEGIN
  v_object_id := :OLD.OBJECT_ID;
  
  UPDATE OBJECTS
  SET OBJECT_VERSION = 
  (SELECT OBJ.OBJECT_VERSION + 1
  FROM OBJECTS OBJ
  WHERE OBJ.OBJECT_ID = v_object_id)
  WHERE OBJECT_ID = v_object_id
  ;

END;
--------------------------------------------------------------
create or replace TRIGGER update_objreference
AFTER UPDATE ON OBJREFERENCE
FOR EACH ROW

DECLARE
  v_old_object_id NUMBER(20);
  v_new_object_id NUMBER(20);
  v_old_reference NUMBER(20);
  v_new_reference NUMBER(20);

BEGIN
  v_old_object_id := :OLD.OBJECT_ID;
  v_new_object_id := :NEW.OBJECT_ID;
  v_old_reference := :OLD.REFERENCE;
  v_new_reference := :NEW.REFERENCE;


  IF(:OLD.OBJECT_ID = :NEW.OBJECT_ID)
  THEN

  IF(:OLD.REFERENCE != :NEW.REFERENCE)
  THEN

  UPDATE OBJECTS
  SET OBJECT_VERSION =
  (SELECT OBJ.OBJECT_VERSION + 1
  FROM OBJECTS OBJ
  WHERE OBJ.OBJECT_ID = v_old_reference)
  WHERE OBJECT_ID = v_old_reference
  ;

  UPDATE OBJECTS
  SET OBJECT_VERSION =
  (SELECT OBJ.OBJECT_VERSION + 1
  FROM OBJECTS OBJ
  WHERE OBJ.OBJECT_ID = v_new_reference)
  WHERE OBJECT_ID = v_new_reference
  ;

  END IF;
  END IF;


  IF(:OLD.OBJECT_ID != :NEW.OBJECT_ID)
  THEN

  UPDATE OBJECTS
  SET OBJECT_VERSION =
  (SELECT OBJ.OBJECT_VERSION + 1
  FROM OBJECTS OBJ
  WHERE OBJ.OBJECT_ID = v_old_object_id)
  WHERE OBJECT_ID = v_old_object_id
  ;

  UPDATE OBJECTS
  SET OBJECT_VERSION =
  (SELECT OBJ.OBJECT_VERSION + 1
  FROM OBJECTS OBJ
  WHERE OBJ.OBJECT_ID = v_new_object_id)
  WHERE OBJECT_ID = v_new_object_id
  ;
  END IF;
END;
--------------------------------------------------------------
create or replace TRIGGER delete_objreference
AFTER DELETE ON OBJREFERENCE
FOR EACH ROW
DECLARE
object_count NUMBER := 0;
reference_count NUMBER := 0;
BEGIN

SELECT COUNT(OBJECT_ID) INTO object_count
FROM OBJECTS
WHERE OBJECT_ID = :OLD.OBJECT_ID
;

SELECT COUNT(OBJECT_ID) INTO reference_count
FROM OBJECTS
WHERE OBJECT_ID = :OLD.OBJECT_ID
;

IF(object_count != 0)
THEN
IF(reference_count != 0)
THEN

UPDATE OBJECTS
SET OBJECT_VERSION = (
SELECT OBJ.OBJECT_VERSION + 1
FROM OBJECTS OBJ
WHERE OBJ.OBJECT_ID = :OLD.OBJECT_ID
)
WHERE OBJECT_ID = :OLD.OBJECT_ID
;

UPDATE OBJECTS
SET OBJECT_VERSION = (
SELECT OBJ.OBJECT_VERSION + 1
FROM OBJECTS OBJ
WHERE OBJ.OBJECT_ID = :OLD.REFERENCE
)
WHERE OBJECT_ID = :OLD.REFERENCE
;

END IF;
END IF;

END;
-----------------------------------------
create or replace TRIGGER delete_objects_from_temp
    AFTER DELETE ON OBJECTS
    FOR EACH ROW
DECLARE

BEGIN

DELETE FROM OBJECT_VERSION_TEMP
WHERE OBJECT_ID = :OLD.OBJECT_ID;

END;