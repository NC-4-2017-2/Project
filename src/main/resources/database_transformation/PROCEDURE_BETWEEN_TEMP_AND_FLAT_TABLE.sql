create or replace FUNCTION return_counter(obj_type_id IN NUMBER, splitter_num IN NUMBER)
RETURN NUMBER IS
  counter NUMBER(20) := 0;
BEGIN
  SELECT CEIL(COUNT(OBJECT_ID) / splitter_num)
  INTO counter
  FROM OBJECTS
  WHERE OBJECT_TYPE_ID = obj_type_id;
    
  RETURN counter;
END;



create or replace FUNCTION return_object_varray(obj_type_id IN NUMBER, start_rnum IN NUMBER, end_rnum IN NUMBER)
RETURN NUMBER_ARRAY IS
    objects_array NUMBER_ARRAY;
BEGIN
  SELECT OBJECT_ID
  BULK COLLECT INTO objects_array
  FROM
  (SELECT O.OBJECT_ID, O.OBJECT_TYPE_ID, rownum rnum
  FROM OBJECTS O
  WHERE OBJECT_TYPE_ID = obj_type_id)
  WHERE rnum BETWEEN start_rnum AND end_rnum;
  
  RETURN objects_array;
END;


create or replace PROCEDURE commit_merge_from_temp(obj_type_id  IN NUMBER,
                                                   splitter_num IN NUMBER)
IS
  objects_array NUMBER_ARRAY;
  start_rownum  NUMBER := 0;
  end_rownum    NUMBER := splitter_num;
  counter       NUMBER := 0;
  BEGIN
    counter := return_counter(obj_type_id, splitter_num);

    IF (counter != 0)
    THEN

      IF (obj_type_id = 3)
      THEN
        FOR i IN 1..counter LOOP
          objects_array := return_object_varray(obj_type_id, start_rownum,
                                                end_rownum);
          start_rownum := end_rownum + 1;
          end_rownum := end_rownum + splitter_num;
          insert_project_stat_proc(objects_array); --здесь надо вызывать merge
          --сделать коммит
          --COMMIT;
        END LOOP;
      END IF;

      IF (obj_type_id = 5)
      THEN
        FOR i IN 1..counter LOOP
          objects_array := return_object_varray(obj_type_id, start_rownum,
                                                end_rownum);
          start_rownum := end_rownum + 1;
          end_rownum := end_rownum + splitter_num;
          insert_vacation_stat_proc(objects_array); --здесь надо вызывать merge
          --сделать коммит
          --COMMIT;
        END LOOP;
      END IF;

      IF (obj_type_id = 6)
      THEN
        FOR i IN 1..counter LOOP
          objects_array := return_object_varray(obj_type_id, start_rownum,
                                                end_rownum);
          start_rownum := end_rownum + 1;
          end_rownum := end_rownum + splitter_num;
          insert_working_day_stat_proc(
              objects_array); --здесь надо вызывать merge
          --сделать коммит
          --COMMIT;
        END LOOP;
      END IF;

      IF (obj_type_id = 7)
      THEN
        FOR i IN 1..counter LOOP
          objects_array := return_object_varray(obj_type_id, start_rownum,
                                                end_rownum);
          start_rownum := end_rownum + 1;
          end_rownum := end_rownum + splitter_num;
          insert_sprint_into_sprint_stat(
              objects_array); --здесь надо вызывать merge
          --сделать коммит
          --COMMIT;
        END LOOP;
      END IF;

      IF (obj_type_id = 8)
      THEN
        FOR i IN 1..counter LOOP
          objects_array := return_object_varray(obj_type_id, start_rownum,
                                                end_rownum);
          start_rownum := end_rownum + 1;
          end_rownum := end_rownum + splitter_num;
          insert_work_period_stat_proc(
              objects_array); --здесь надо вызывать merge
          --сделать коммит
          --COMMIT;
        END LOOP;
      END IF;

    END IF;
  END;

create or replace PROCEDURE merge_from_temp_to_tables
IS
  counter       NUMBER := 0;
  splitter_num  NUMBER := 2;
BEGIN

    SELECT CEIL(COUNT(OBJECT_ID) / splitter_num)
    INTO counter
    FROM OBJECT_VERSION_TEMP
    WHERE OBJ_TYPE_ID = 3;
    IF (counter != 0)
    THEN
      commit_merge_from_temp(3, splitter_num);
    END IF;

    SELECT CEIL(COUNT(OBJECT_ID) / splitter_num)
    INTO counter
    FROM OBJECT_VERSION_TEMP
    WHERE OBJ_TYPE_ID = 5;
    IF (counter != 0)
    THEN
      commit_merge_from_temp(5, splitter_num);
    END IF;

    SELECT CEIL(COUNT(OBJECT_ID) / splitter_num)
    INTO counter
    FROM OBJECT_VERSION_TEMP
    WHERE OBJ_TYPE_ID = 6;
    IF (counter != 0)
    THEN
      commit_merge_from_temp(6, splitter_num);
    END IF;


    SELECT CEIL(COUNT(OBJECT_ID) / splitter_num)
    INTO counter
    FROM OBJECT_VERSION_TEMP
    WHERE OBJ_TYPE_ID = 7;
    IF (counter != 0)
    THEN
      commit_merge_from_temp(7, splitter_num);
    END IF;

    SELECT CEIL(COUNT(OBJECT_ID) / splitter_num)
    INTO counter
    FROM OBJECT_VERSION_TEMP
    WHERE OBJ_TYPE_ID = 8;
    IF (counter != 0)
    THEN
      commit_merge_from_temp(8, splitter_num);
    END IF;

    --delete from temp DONT FORGET
END;