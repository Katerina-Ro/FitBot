PGDMP     :    -                z            passdb    14.2    14.2     E           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            F           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            G           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            H           1262    16394    passdb    DATABASE     c   CREATE DATABASE passdb WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'Russian_Russia.1251';
    DROP DATABASE passdb;
                postgres    false            �           2606    24699     bot_scheduler bot_scheduler_pkey 
   CONSTRAINT     f   ALTER TABLE ONLY pass_schema.bot_scheduler
    ADD CONSTRAINT bot_scheduler_pkey PRIMARY KEY (ivent);
 O   ALTER TABLE ONLY pass_schema.bot_scheduler DROP CONSTRAINT bot_scheduler_pkey;
       pass_schema            postgres    false            �           2606    16418    visits pk_pass_date 
   CONSTRAINT     g   ALTER TABLE ONLY pass_schema.visits
    ADD CONSTRAINT pk_pass_date PRIMARY KEY (pass_id, date_visit);
 B   ALTER TABLE ONLY pass_schema.visits DROP CONSTRAINT pk_pass_date;
       pass_schema            postgres    false            �           2606    16408    pass_table pk_pass_id 
   CONSTRAINT     ]   ALTER TABLE ONLY pass_schema.pass_table
    ADD CONSTRAINT pk_pass_id PRIMARY KEY (pass_id);
 D   ALTER TABLE ONLY pass_schema.pass_table DROP CONSTRAINT pk_pass_id;
       pass_schema            postgres    false            �           2606    16477    visitors pk_visitors 
   CONSTRAINT     \   ALTER TABLE ONLY pass_schema.visitors
    ADD CONSTRAINT pk_visitors PRIMARY KEY (tel_num);
 C   ALTER TABLE ONLY pass_schema.visitors DROP CONSTRAINT pk_visitors;
       pass_schema            postgres    false            �           2606    16749 2   pass_schema_pass_table pass_schema_pass_table_pkey 
   CONSTRAINT     u   ALTER TABLE ONLY public.pass_schema_pass_table
    ADD CONSTRAINT pass_schema_pass_table_pkey PRIMARY KEY (pass_id);
 \   ALTER TABLE ONLY public.pass_schema_pass_table DROP CONSTRAINT pass_schema_pass_table_pkey;
       public            postgres    false            �           2606    16759 .   pass_schema_visitors pass_schema_visitors_pkey 
   CONSTRAINT     q   ALTER TABLE ONLY public.pass_schema_visitors
    ADD CONSTRAINT pass_schema_visitors_pkey PRIMARY KEY (tel_num);
 X   ALTER TABLE ONLY public.pass_schema_visitors DROP CONSTRAINT pass_schema_visitors_pkey;
       public            postgres    false            �           2606    16767 *   pass_schema_visits pass_schema_visits_pkey 
   CONSTRAINT     y   ALTER TABLE ONLY public.pass_schema_visits
    ADD CONSTRAINT pass_schema_visits_pkey PRIMARY KEY (date_visit, pass_id);
 T   ALTER TABLE ONLY public.pass_schema_visits DROP CONSTRAINT pass_schema_visits_pkey;
       public            postgres    false            �           2606    16769 :   pass_schema_pass_table_visits uk_b6auh8f8vmigkm6yqo4wtocbh 
   CONSTRAINT     �   ALTER TABLE ONLY public.pass_schema_pass_table_visits
    ADD CONSTRAINT uk_b6auh8f8vmigkm6yqo4wtocbh UNIQUE (visits_date_visit, visits_pass_id);
 d   ALTER TABLE ONLY public.pass_schema_pass_table_visits DROP CONSTRAINT uk_b6auh8f8vmigkm6yqo4wtocbh;
       public            postgres    false            �           2606    16771 1   pass_schema_visitors uk_lachruul8ymwyg4e5j0e6091y 
   CONSTRAINT     o   ALTER TABLE ONLY public.pass_schema_visitors
    ADD CONSTRAINT uk_lachruul8ymwyg4e5j0e6091y UNIQUE (chat_id);
 [   ALTER TABLE ONLY public.pass_schema_visitors DROP CONSTRAINT uk_lachruul8ymwyg4e5j0e6091y;
       public            postgres    false            �           2606    16773 ;   pass_schema_visitors_pass_list uk_s04c78hvuapaxa9mm5vivimaj 
   CONSTRAINT     �   ALTER TABLE ONLY public.pass_schema_visitors_pass_list
    ADD CONSTRAINT uk_s04c78hvuapaxa9mm5vivimaj UNIQUE (pass_list_pass_id);
 e   ALTER TABLE ONLY public.pass_schema_visitors_pass_list DROP CONSTRAINT uk_s04c78hvuapaxa9mm5vivimaj;
       public            postgres    false            �           1259    16483    fki_pass_table    INDEX     M   CREATE INDEX fki_pass_table ON pass_schema.pass_table USING btree (tel_num);
 '   DROP INDEX pass_schema.fki_pass_table;
       pass_schema            postgres    false            �           2606    16805 *   dont_come_today fk_dont_come_today_tel_num    FK CONSTRAINT     �   ALTER TABLE ONLY pass_schema.dont_come_today
    ADD CONSTRAINT fk_dont_come_today_tel_num FOREIGN KEY (tel_num) REFERENCES pass_schema.visitors(tel_num) ON UPDATE CASCADE ON DELETE CASCADE;
 Y   ALTER TABLE ONLY pass_schema.dont_come_today DROP CONSTRAINT fk_dont_come_today_tel_num;
       pass_schema          postgres    false    3214            �           2606    16419    visits fk_pass_id_in_pass_table    FK CONSTRAINT     �   ALTER TABLE ONLY pass_schema.visits
    ADD CONSTRAINT fk_pass_id_in_pass_table FOREIGN KEY (pass_id) REFERENCES pass_schema.pass_table(pass_id) ON UPDATE CASCADE ON DELETE CASCADE;
 N   ALTER TABLE ONLY pass_schema.visits DROP CONSTRAINT fk_pass_id_in_pass_table;
       pass_schema          postgres    false    3217            �           2606    16810    come_today fk_today_tel_num    FK CONSTRAINT     �   ALTER TABLE ONLY pass_schema.come_today
    ADD CONSTRAINT fk_today_tel_num FOREIGN KEY (tel_num) REFERENCES pass_schema.visitors(tel_num) ON UPDATE CASCADE ON DELETE CASCADE NOT VALID;
 J   ALTER TABLE ONLY pass_schema.come_today DROP CONSTRAINT fk_today_tel_num;
       pass_schema          postgres    false    3214            �           2606    16478    pass_table pass_table    FK CONSTRAINT     �   ALTER TABLE ONLY pass_schema.pass_table
    ADD CONSTRAINT pass_table FOREIGN KEY (tel_num) REFERENCES pass_schema.visitors(tel_num) ON UPDATE CASCADE;
 D   ALTER TABLE ONLY pass_schema.pass_table DROP CONSTRAINT pass_table;
       pass_schema          postgres    false    3214            �           2606    16779 9   pass_schema_pass_table_visits fk8ictbxkrvy6gxw0rlvmn2ccet    FK CONSTRAINT     �   ALTER TABLE ONLY public.pass_schema_pass_table_visits
    ADD CONSTRAINT fk8ictbxkrvy6gxw0rlvmn2ccet FOREIGN KEY (pass_pass_id) REFERENCES public.pass_schema_pass_table(pass_id);
 c   ALTER TABLE ONLY public.pass_schema_pass_table_visits DROP CONSTRAINT fk8ictbxkrvy6gxw0rlvmn2ccet;
       public          postgres    false    3221            �           2606    16784 :   pass_schema_visitors_pass_list fkmu4d7gnuifjc8eoqmkax8oycc    FK CONSTRAINT     �   ALTER TABLE ONLY public.pass_schema_visitors_pass_list
    ADD CONSTRAINT fkmu4d7gnuifjc8eoqmkax8oycc FOREIGN KEY (pass_list_pass_id) REFERENCES public.pass_schema_pass_table(pass_id);
 d   ALTER TABLE ONLY public.pass_schema_visitors_pass_list DROP CONSTRAINT fkmu4d7gnuifjc8eoqmkax8oycc;
       public          postgres    false    3221            �           2606    16774 9   pass_schema_pass_table_visits fkqn189nbk4frq9sjce3fxcy3gs    FK CONSTRAINT     �   ALTER TABLE ONLY public.pass_schema_pass_table_visits
    ADD CONSTRAINT fkqn189nbk4frq9sjce3fxcy3gs FOREIGN KEY (visits_date_visit, visits_pass_id) REFERENCES public.pass_schema_visits(date_visit, pass_id);
 c   ALTER TABLE ONLY public.pass_schema_pass_table_visits DROP CONSTRAINT fkqn189nbk4frq9sjce3fxcy3gs;
       public          postgres    false    3231            �           2606    16789 :   pass_schema_visitors_pass_list fkrcopbsi1vfyv1satmvq0b98og    FK CONSTRAINT     �   ALTER TABLE ONLY public.pass_schema_visitors_pass_list
    ADD CONSTRAINT fkrcopbsi1vfyv1satmvq0b98og FOREIGN KEY (visitors_tel_num) REFERENCES public.pass_schema_visitors(tel_num);
 d   ALTER TABLE ONLY public.pass_schema_visitors_pass_list DROP CONSTRAINT fkrcopbsi1vfyv1satmvq0b98og;
       public          postgres    false    3225           