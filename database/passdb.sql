PGDMP         (                z            passdb    14.2    14.2 P    C           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            D           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            E           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            F           1262    16394    passdb    DATABASE     c   CREATE DATABASE passdb WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'Russian_Russia.1251';
    DROP DATABASE passdb;
                postgres    false            G           0    0    DATABASE passdb    COMMENT     E   COMMENT ON DATABASE passdb IS 'Клиенты-абонементы';
                   postgres    false    3398                        2615    16396    pass_schema    SCHEMA        CREATE SCHEMA pass_schema;
    DROP SCHEMA pass_schema;
                postgres    false            H           0    0    SCHEMA pass_schema    COMMENT     v   COMMENT ON SCHEMA pass_schema IS 'Схема всех объектов для расчета абонементов';
                   postgres    false    6            I           0    0    SCHEMA pass_schema    ACL     ^   GRANT USAGE ON SCHEMA pass_schema TO bot_user;
GRANT USAGE ON SCHEMA pass_schema TO app_user;
                   postgres    false    6            �            1259    24695    bot_scheduler    TABLE     �   CREATE TABLE pass_schema.bot_scheduler (
    ivent character varying(200) NOT NULL,
    ivent_time time without time zone NOT NULL
);
 &   DROP TABLE pass_schema.bot_scheduler;
       pass_schema         heap    postgres    false    6            J           0    0    TABLE bot_scheduler    ACL     �   GRANT SELECT ON TABLE pass_schema.bot_scheduler TO bot_user;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE pass_schema.bot_scheduler TO app_user;
          pass_schema          postgres    false    223            �            1259    16815    come_not_in_db    TABLE     l   CREATE TABLE pass_schema.come_not_in_db (
    chat_id bigint,
    tel_num character varying(11) NOT NULL
);
 '   DROP TABLE pass_schema.come_not_in_db;
       pass_schema         heap    postgres    false    6            K           0    0    TABLE come_not_in_db    COMMENT     i   COMMENT ON TABLE pass_schema.come_not_in_db IS 'планирует придти, но не в базе';
          pass_schema          postgres    false    222            L           0    0    TABLE come_not_in_db    ACL     S   GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE pass_schema.come_not_in_db TO app_user;
          pass_schema          postgres    false    222            �            1259    16794 
   come_today    TABLE     �   CREATE TABLE pass_schema.come_today (
    "Chat_Id" bigint,
    tel_num character varying(11) NOT NULL,
    surname character varying(100),
    name character varying(100) NOT NULL,
    patronymic character varying(100),
    currency_date date
);
 #   DROP TABLE pass_schema.come_today;
       pass_schema         heap    postgres    false    6            M           0    0    TABLE come_today    COMMENT     ]   COMMENT ON TABLE pass_schema.come_today IS 'планирует придти сегодня';
          pass_schema          postgres    false    220            N           0    0    TABLE come_today    ACL     �   GRANT INSERT ON TABLE pass_schema.come_today TO bot_user;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE pass_schema.come_today TO app_user;
          pass_schema          postgres    false    220            �            1259    16802    dont_come_today    TABLE     �   CREATE TABLE pass_schema.dont_come_today (
    chat_id bigint,
    tel_num character varying(11) NOT NULL,
    surname character varying(100),
    name character varying(100) NOT NULL,
    patronymic character varying(100),
    currency_date date
);
 (   DROP TABLE pass_schema.dont_come_today;
       pass_schema         heap    postgres    false    6            O           0    0    TABLE dont_come_today    COMMENT     g   COMMENT ON TABLE pass_schema.dont_come_today IS 'не планирует придти сегодня';
          pass_schema          postgres    false    221            P           0    0    TABLE dont_come_today    ACL     �   GRANT INSERT ON TABLE pass_schema.dont_come_today TO bot_user;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE pass_schema.dont_come_today TO app_user;
          pass_schema          postgres    false    221            �            1259    16403 
   pass_table    TABLE     9  CREATE TABLE pass_schema.pass_table (
    pass_id integer NOT NULL,
    date_start date NOT NULL,
    date_end date NOT NULL,
    visit_limit integer NOT NULL,
    tel_num character varying(11) NOT NULL,
    freeze_limit integer,
    date_freeze date,
    CONSTRAINT date_check CHECK ((date_start < date_end))
);
 #   DROP TABLE pass_schema.pass_table;
       pass_schema         heap    postgres    false    6            Q           0    0    TABLE pass_table    COMMENT     �   COMMENT ON TABLE pass_schema.pass_table IS 'Абонементы, принадлежность людям, даты действительности, лимит посещений';
          pass_schema          postgres    false    211            R           0    0    COLUMN pass_table.pass_id    COMMENT     l   COMMENT ON COLUMN pass_schema.pass_table.pass_id IS 'Уникальный номер абонемента';
          pass_schema          postgres    false    211            S           0    0    COLUMN pass_table.date_start    COMMENT     �   COMMENT ON COLUMN pass_schema.pass_table.date_start IS 'дата начала действия абонемента - включительно';
          pass_schema          postgres    false    211            T           0    0    COLUMN pass_table.date_end    COMMENT     �   COMMENT ON COLUMN pass_schema.pass_table.date_end IS 'дата окончания действия абонемента - включительно';
          pass_schema          postgres    false    211            U           0    0    COLUMN pass_table.visit_limit    COMMENT     �   COMMENT ON COLUMN pass_schema.pass_table.visit_limit IS 'лимит занятий в конкретном абонементе';
          pass_schema          postgres    false    211            V           0    0 #   CONSTRAINT date_check ON pass_table    COMMENT     �   COMMENT ON CONSTRAINT date_check ON pass_schema.pass_table IS 'дата начала абонемента всегда меньше даты конца абонемента';
          pass_schema          postgres    false    211            W           0    0    TABLE pass_table    ACL     �   GRANT SELECT ON TABLE pass_schema.pass_table TO bot_user;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE pass_schema.pass_table TO app_user;
          pass_schema          postgres    false    211            �            1259    16433    pass_table_pass_id_seq    SEQUENCE     �   ALTER TABLE pass_schema.pass_table ALTER COLUMN pass_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME pass_schema.pass_table_pass_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            pass_schema          postgres    false    211    6            �            1259    16398    visitors    TABLE     �   CREATE TABLE pass_schema.visitors (
    surname character varying(100),
    name character varying(100),
    patronumic character varying(100),
    tel_num character varying(11) NOT NULL,
    chat_id bigint
);
 !   DROP TABLE pass_schema.visitors;
       pass_schema         heap    postgres    false    6            X           0    0    TABLE visitors    COMMENT     P   COMMENT ON TABLE pass_schema.visitors IS 'Список посетителей';
          pass_schema          postgres    false    210            Y           0    0    COLUMN visitors.surname    COMMENT     D   COMMENT ON COLUMN pass_schema.visitors.surname IS 'Фамилия';
          pass_schema          postgres    false    210            Z           0    0    COLUMN visitors.name    COMMENT     9   COMMENT ON COLUMN pass_schema.visitors.name IS 'Имя';
          pass_schema          postgres    false    210            [           0    0    COLUMN visitors.patronumic    COMMENT     I   COMMENT ON COLUMN pass_schema.visitors.patronumic IS 'Отчество';
          pass_schema          postgres    false    210            \           0    0    COLUMN visitors.tel_num    COMMENT     �   COMMENT ON COLUMN pass_schema.visitors.tel_num IS 'телефон только цифры с кодом страны. Пример 89991112233';
          pass_schema          postgres    false    210            ]           0    0    TABLE visitors    ACL     �   GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE pass_schema.visitors TO app_user;
GRANT SELECT ON TABLE pass_schema.visitors TO bot_user;
          pass_schema          postgres    false    210            �            1259    16414    visits    TABLE     �   CREATE TABLE pass_schema.visits (
    pass_id integer NOT NULL,
    date_visit date NOT NULL,
    count_visit integer NOT NULL
);
    DROP TABLE pass_schema.visits;
       pass_schema         heap    postgres    false    6            ^           0    0    TABLE visits    COMMENT     k   COMMENT ON TABLE pass_schema.visits IS 'отметки о посещениях в абонементах';
          pass_schema          postgres    false    212            _           0    0    COLUMN visits.pass_id    COMMENT     |   COMMENT ON COLUMN pass_schema.visits.pass_id IS 'номер абонемента для отметки посещения';
          pass_schema          postgres    false    212            `           0    0    COLUMN visits.date_visit    COMMENT     R   COMMENT ON COLUMN pass_schema.visits.date_visit IS 'дата посещения';
          pass_schema          postgres    false    212            a           0    0    COLUMN visits.count_visit    COMMENT     b   COMMENT ON COLUMN pass_schema.visits.count_visit IS 'кол-во посещений в дате';
          pass_schema          postgres    false    212            b           0    0    TABLE visits    ACL     �   GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE pass_schema.visits TO app_user;
GRANT SELECT ON TABLE pass_schema.visits TO bot_user;
          pass_schema          postgres    false    212            �            1259    16460    hibernate_sequence    SEQUENCE     {   CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public.hibernate_sequence;
       public          bot_user    false            �            1259    16745    pass_schema_pass_table    TABLE     �   CREATE TABLE public.pass_schema_pass_table (
    pass_id integer NOT NULL,
    date_end date NOT NULL,
    date_start date NOT NULL,
    date_freeze date,
    freeze_limit integer,
    tel_num character varying(255),
    visit_limit integer NOT NULL
);
 *   DROP TABLE public.pass_schema_pass_table;
       public         heap    postgres    false            �            1259    16750    pass_schema_pass_table_visits    TABLE     �   CREATE TABLE public.pass_schema_pass_table_visits (
    pass_pass_id integer NOT NULL,
    visits_date_visit date NOT NULL,
    visits_pass_id integer NOT NULL
);
 1   DROP TABLE public.pass_schema_pass_table_visits;
       public         heap    postgres    false            �            1259    16753    pass_schema_visitors    TABLE     �   CREATE TABLE public.pass_schema_visitors (
    tel_num character varying(255) NOT NULL,
    chat_id bigint,
    name character varying(255),
    patronumic character varying(255),
    surname character varying(255)
);
 (   DROP TABLE public.pass_schema_visitors;
       public         heap    postgres    false            �            1259    16760    pass_schema_visitors_pass_list    TABLE     �   CREATE TABLE public.pass_schema_visitors_pass_list (
    visitors_tel_num character varying(255) NOT NULL,
    pass_list_pass_id integer NOT NULL
);
 2   DROP TABLE public.pass_schema_visitors_pass_list;
       public         heap    postgres    false            �            1259    16763    pass_schema_visits    TABLE     �   CREATE TABLE public.pass_schema_visits (
    date_visit date NOT NULL,
    pass_id integer NOT NULL,
    count_visit integer
);
 &   DROP TABLE public.pass_schema_visits;
       public         heap    postgres    false            @          0    24695    bot_scheduler 
   TABLE DATA                 pass_schema          postgres    false    223   x\       ?          0    16815    come_not_in_db 
   TABLE DATA                 pass_schema          postgres    false    222   �\       =          0    16794 
   come_today 
   TABLE DATA                 pass_schema          postgres    false    220   �\       >          0    16802    dont_come_today 
   TABLE DATA                 pass_schema          postgres    false    221   �\       4          0    16403 
   pass_table 
   TABLE DATA                 pass_schema          postgres    false    211   �\       3          0    16398    visitors 
   TABLE DATA                 pass_schema          postgres    false    210   �]       5          0    16414    visits 
   TABLE DATA                 pass_schema          postgres    false    212   g^       8          0    16745    pass_schema_pass_table 
   TABLE DATA                 public          postgres    false    215   �^       9          0    16750    pass_schema_pass_table_visits 
   TABLE DATA                 public          postgres    false    216   �^       :          0    16753    pass_schema_visitors 
   TABLE DATA                 public          postgres    false    217   	_       ;          0    16760    pass_schema_visitors_pass_list 
   TABLE DATA                 public          postgres    false    218   #_       <          0    16763    pass_schema_visits 
   TABLE DATA                 public          postgres    false    219   =_       c           0    0    pass_table_pass_id_seq    SEQUENCE SET     I   SELECT pg_catalog.setval('pass_schema.pass_table_pass_id_seq', 4, true);
          pass_schema          postgres    false    213            d           0    0    hibernate_sequence    SEQUENCE SET     @   SELECT pg_catalog.setval('public.hibernate_sequence', 1, true);
          public          bot_user    false    214            �           2606    24699     bot_scheduler bot_scheduler_pkey 
   CONSTRAINT     f   ALTER TABLE ONLY pass_schema.bot_scheduler
    ADD CONSTRAINT bot_scheduler_pkey PRIMARY KEY (ivent);
 O   ALTER TABLE ONLY pass_schema.bot_scheduler DROP CONSTRAINT bot_scheduler_pkey;
       pass_schema            postgres    false    223            �           2606    16418    visits pk_pass_date 
   CONSTRAINT     g   ALTER TABLE ONLY pass_schema.visits
    ADD CONSTRAINT pk_pass_date PRIMARY KEY (pass_id, date_visit);
 B   ALTER TABLE ONLY pass_schema.visits DROP CONSTRAINT pk_pass_date;
       pass_schema            postgres    false    212    212            �           2606    16408    pass_table pk_pass_id 
   CONSTRAINT     ]   ALTER TABLE ONLY pass_schema.pass_table
    ADD CONSTRAINT pk_pass_id PRIMARY KEY (pass_id);
 D   ALTER TABLE ONLY pass_schema.pass_table DROP CONSTRAINT pk_pass_id;
       pass_schema            postgres    false    211            �           2606    16477    visitors pk_visitors 
   CONSTRAINT     \   ALTER TABLE ONLY pass_schema.visitors
    ADD CONSTRAINT pk_visitors PRIMARY KEY (tel_num);
 C   ALTER TABLE ONLY pass_schema.visitors DROP CONSTRAINT pk_visitors;
       pass_schema            postgres    false    210            �           2606    16749 2   pass_schema_pass_table pass_schema_pass_table_pkey 
   CONSTRAINT     u   ALTER TABLE ONLY public.pass_schema_pass_table
    ADD CONSTRAINT pass_schema_pass_table_pkey PRIMARY KEY (pass_id);
 \   ALTER TABLE ONLY public.pass_schema_pass_table DROP CONSTRAINT pass_schema_pass_table_pkey;
       public            postgres    false    215            �           2606    16759 .   pass_schema_visitors pass_schema_visitors_pkey 
   CONSTRAINT     q   ALTER TABLE ONLY public.pass_schema_visitors
    ADD CONSTRAINT pass_schema_visitors_pkey PRIMARY KEY (tel_num);
 X   ALTER TABLE ONLY public.pass_schema_visitors DROP CONSTRAINT pass_schema_visitors_pkey;
       public            postgres    false    217            �           2606    16767 *   pass_schema_visits pass_schema_visits_pkey 
   CONSTRAINT     y   ALTER TABLE ONLY public.pass_schema_visits
    ADD CONSTRAINT pass_schema_visits_pkey PRIMARY KEY (date_visit, pass_id);
 T   ALTER TABLE ONLY public.pass_schema_visits DROP CONSTRAINT pass_schema_visits_pkey;
       public            postgres    false    219    219            �           2606    16769 :   pass_schema_pass_table_visits uk_b6auh8f8vmigkm6yqo4wtocbh 
   CONSTRAINT     �   ALTER TABLE ONLY public.pass_schema_pass_table_visits
    ADD CONSTRAINT uk_b6auh8f8vmigkm6yqo4wtocbh UNIQUE (visits_date_visit, visits_pass_id);
 d   ALTER TABLE ONLY public.pass_schema_pass_table_visits DROP CONSTRAINT uk_b6auh8f8vmigkm6yqo4wtocbh;
       public            postgres    false    216    216            �           2606    16771 1   pass_schema_visitors uk_lachruul8ymwyg4e5j0e6091y 
   CONSTRAINT     o   ALTER TABLE ONLY public.pass_schema_visitors
    ADD CONSTRAINT uk_lachruul8ymwyg4e5j0e6091y UNIQUE (chat_id);
 [   ALTER TABLE ONLY public.pass_schema_visitors DROP CONSTRAINT uk_lachruul8ymwyg4e5j0e6091y;
       public            postgres    false    217            �           2606    16773 ;   pass_schema_visitors_pass_list uk_s04c78hvuapaxa9mm5vivimaj 
   CONSTRAINT     �   ALTER TABLE ONLY public.pass_schema_visitors_pass_list
    ADD CONSTRAINT uk_s04c78hvuapaxa9mm5vivimaj UNIQUE (pass_list_pass_id);
 e   ALTER TABLE ONLY public.pass_schema_visitors_pass_list DROP CONSTRAINT uk_s04c78hvuapaxa9mm5vivimaj;
       public            postgres    false    218            �           1259    16483    fki_pass_table    INDEX     M   CREATE INDEX fki_pass_table ON pass_schema.pass_table USING btree (tel_num);
 '   DROP INDEX pass_schema.fki_pass_table;
       pass_schema            postgres    false    211            �           2606    16805 *   dont_come_today fk_dont_come_today_tel_num    FK CONSTRAINT     �   ALTER TABLE ONLY pass_schema.dont_come_today
    ADD CONSTRAINT fk_dont_come_today_tel_num FOREIGN KEY (tel_num) REFERENCES pass_schema.visitors(tel_num) ON UPDATE CASCADE ON DELETE CASCADE;
 Y   ALTER TABLE ONLY pass_schema.dont_come_today DROP CONSTRAINT fk_dont_come_today_tel_num;
       pass_schema          postgres    false    210    3212    221            �           2606    16419    visits fk_pass_id_in_pass_table    FK CONSTRAINT     �   ALTER TABLE ONLY pass_schema.visits
    ADD CONSTRAINT fk_pass_id_in_pass_table FOREIGN KEY (pass_id) REFERENCES pass_schema.pass_table(pass_id) ON UPDATE CASCADE ON DELETE CASCADE;
 N   ALTER TABLE ONLY pass_schema.visits DROP CONSTRAINT fk_pass_id_in_pass_table;
       pass_schema          postgres    false    211    212    3215            �           2606    16810    come_today fk_today_tel_num    FK CONSTRAINT     �   ALTER TABLE ONLY pass_schema.come_today
    ADD CONSTRAINT fk_today_tel_num FOREIGN KEY (tel_num) REFERENCES pass_schema.visitors(tel_num) ON UPDATE CASCADE ON DELETE CASCADE NOT VALID;
 J   ALTER TABLE ONLY pass_schema.come_today DROP CONSTRAINT fk_today_tel_num;
       pass_schema          postgres    false    220    210    3212            �           2606    16478    pass_table pass_table    FK CONSTRAINT     �   ALTER TABLE ONLY pass_schema.pass_table
    ADD CONSTRAINT pass_table FOREIGN KEY (tel_num) REFERENCES pass_schema.visitors(tel_num) ON UPDATE CASCADE;
 D   ALTER TABLE ONLY pass_schema.pass_table DROP CONSTRAINT pass_table;
       pass_schema          postgres    false    3212    210    211            �           2606    16779 9   pass_schema_pass_table_visits fk8ictbxkrvy6gxw0rlvmn2ccet    FK CONSTRAINT     �   ALTER TABLE ONLY public.pass_schema_pass_table_visits
    ADD CONSTRAINT fk8ictbxkrvy6gxw0rlvmn2ccet FOREIGN KEY (pass_pass_id) REFERENCES public.pass_schema_pass_table(pass_id);
 c   ALTER TABLE ONLY public.pass_schema_pass_table_visits DROP CONSTRAINT fk8ictbxkrvy6gxw0rlvmn2ccet;
       public          postgres    false    216    215    3219            �           2606    16784 :   pass_schema_visitors_pass_list fkmu4d7gnuifjc8eoqmkax8oycc    FK CONSTRAINT     �   ALTER TABLE ONLY public.pass_schema_visitors_pass_list
    ADD CONSTRAINT fkmu4d7gnuifjc8eoqmkax8oycc FOREIGN KEY (pass_list_pass_id) REFERENCES public.pass_schema_pass_table(pass_id);
 d   ALTER TABLE ONLY public.pass_schema_visitors_pass_list DROP CONSTRAINT fkmu4d7gnuifjc8eoqmkax8oycc;
       public          postgres    false    218    215    3219            �           2606    16774 9   pass_schema_pass_table_visits fkqn189nbk4frq9sjce3fxcy3gs    FK CONSTRAINT     �   ALTER TABLE ONLY public.pass_schema_pass_table_visits
    ADD CONSTRAINT fkqn189nbk4frq9sjce3fxcy3gs FOREIGN KEY (visits_date_visit, visits_pass_id) REFERENCES public.pass_schema_visits(date_visit, pass_id);
 c   ALTER TABLE ONLY public.pass_schema_pass_table_visits DROP CONSTRAINT fkqn189nbk4frq9sjce3fxcy3gs;
       public          postgres    false    219    216    216    219    3229            �           2606    16789 :   pass_schema_visitors_pass_list fkrcopbsi1vfyv1satmvq0b98og    FK CONSTRAINT     �   ALTER TABLE ONLY public.pass_schema_visitors_pass_list
    ADD CONSTRAINT fkrcopbsi1vfyv1satmvq0b98og FOREIGN KEY (visitors_tel_num) REFERENCES public.pass_schema_visitors(tel_num);
 d   ALTER TABLE ONLY public.pass_schema_visitors_pass_list DROP CONSTRAINT fkrcopbsi1vfyv1satmvq0b98og;
       public          postgres    false    218    3223    217            @   
   x���          ?   
   x���          =   
   x���          >   
   x���          4   �   x�=�Q�0�w�}SAc,-�����T�i�\4P	�z��7���ᜏK'U��%<�1�\�j���[y�s�]��J+'��jt��F[��A;���s��6)�V?��
�lIU�eG�g^����!Kr�|�0�Q���_�;�2�<Em�5ʝ��(�w��} s_>�      3   �   x���v
Q���W(H,.�/N�H�M�+�,�,�/*V�(.-�K�M�Q���%E�y����:
%�9�@��BrFbI|f��B��O�k������]l������u�/L����M�^l������ ��-M��-�,��f��&���F��\�4r�Es.� �e�-[ nTGq�ԁ�v�$p��t�!�\\ |Ù;      5   ^   x���v
Q���W(H,.�/N�H�M�+�,�,)V� �e��(�$��ƃEu��K�J M�0G�P�`u###]]CsuCMk... H�g      8   
   x���          9   
   x���          :   
   x���          ;   
   x���          <   
   x���         