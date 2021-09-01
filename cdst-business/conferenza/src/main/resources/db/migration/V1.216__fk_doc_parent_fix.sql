DO $$ 
    BEGIN
        BEGIN
            ALTER TABLE cdst.documento ADD fk_doc_parent int NULL;
        EXCEPTION
            WHEN duplicate_column THEN RAISE NOTICE 'column <column_name> already exists in <table_name>.';
        END;
    END;
$$