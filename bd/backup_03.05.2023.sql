PGDMP     9    %                {            transport_company    15.2    15.2 C    D           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            E           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            F           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            G           1262    16558    transport_company    DATABASE     �   CREATE DATABASE transport_company WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Russian_Russia.1251';
 !   DROP DATABASE transport_company;
                postgres    false            �            1259    16559    drivers    TABLE     v   CREATE TABLE public.drivers (
    id bigint NOT NULL,
    first_name text,
    last_name text,
    patronymic text
);
    DROP TABLE public.drivers;
       public         heap    postgres    false            �            1259    16564    drivers_id_seq    SEQUENCE     w   CREATE SEQUENCE public.drivers_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public.drivers_id_seq;
       public          postgres    false    214            H           0    0    drivers_id_seq    SEQUENCE OWNED BY     A   ALTER SEQUENCE public.drivers_id_seq OWNED BY public.drivers.id;
          public          postgres    false    215            �            1259    16565 
   end_points    TABLE     P   CREATE TABLE public.end_points (
    id bigint NOT NULL,
    point_name text
);
    DROP TABLE public.end_points;
       public         heap    postgres    false            �            1259    16570    end_points_id_seq    SEQUENCE     z   CREATE SEQUENCE public.end_points_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE public.end_points_id_seq;
       public          postgres    false    216            I           0    0    end_points_id_seq    SEQUENCE OWNED BY     G   ALTER SEQUENCE public.end_points_id_seq OWNED BY public.end_points.id;
          public          postgres    false    217            �            1259    16571    route_intervals    TABLE     X   CREATE TABLE public.route_intervals (
    id bigint NOT NULL,
    interval_name text
);
 #   DROP TABLE public.route_intervals;
       public         heap    postgres    false            �            1259    16576    route_intervals_id_seq    SEQUENCE        CREATE SEQUENCE public.route_intervals_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 -   DROP SEQUENCE public.route_intervals_id_seq;
       public          postgres    false    218            J           0    0    route_intervals_id_seq    SEQUENCE OWNED BY     Q   ALTER SEQUENCE public.route_intervals_id_seq OWNED BY public.route_intervals.id;
          public          postgres    false    219            �            1259    16577    routes    TABLE       CREATE TABLE public.routes (
    id bigint NOT NULL,
    route_number character varying(4),
    route_start_point bigint,
    route_finish_point bigint,
    route_time integer,
    route_interval bigint,
    cost_for_adult integer,
    cost_for_child integer
);
    DROP TABLE public.routes;
       public         heap    postgres    false            �            1259    16580    routes_id_seq    SEQUENCE     v   CREATE SEQUENCE public.routes_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 $   DROP SEQUENCE public.routes_id_seq;
       public          postgres    false    220            K           0    0    routes_id_seq    SEQUENCE OWNED BY     ?   ALTER SEQUENCE public.routes_id_seq OWNED BY public.routes.id;
          public          postgres    false    221            �            1259    16581    schedule    TABLE     �   CREATE TABLE public.schedule (
    id bigint NOT NULL,
    point bigint,
    route bigint,
    time_start time without time zone
);
    DROP TABLE public.schedule;
       public         heap    postgres    false            �            1259    16584    schedule_id_seq    SEQUENCE     x   CREATE SEQUENCE public.schedule_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public.schedule_id_seq;
       public          postgres    false    222            L           0    0    schedule_id_seq    SEQUENCE OWNED BY     C   ALTER SEQUENCE public.schedule_id_seq OWNED BY public.schedule.id;
          public          postgres    false    223            �            1259    16585    transport_types    TABLE     X   CREATE TABLE public.transport_types (
    id bigint NOT NULL,
    name text NOT NULL
);
 #   DROP TABLE public.transport_types;
       public         heap    postgres    false            �            1259    16590    transport_type_id_seq    SEQUENCE     ~   CREATE SEQUENCE public.transport_type_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 ,   DROP SEQUENCE public.transport_type_id_seq;
       public          postgres    false    224            M           0    0    transport_type_id_seq    SEQUENCE OWNED BY     P   ALTER SEQUENCE public.transport_type_id_seq OWNED BY public.transport_types.id;
          public          postgres    false    225            �            1259    16591 
   transports    TABLE     �   CREATE TABLE public.transports (
    id bigint NOT NULL,
    transport_type bigint,
    gov_number character varying(9),
    car_brand text,
    seats_count integer,
    driver bigint
);
    DROP TABLE public.transports;
       public         heap    postgres    false            �            1259    16596    transports_id_seq    SEQUENCE     z   CREATE SEQUENCE public.transports_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE public.transports_id_seq;
       public          postgres    false    226            N           0    0    transports_id_seq    SEQUENCE OWNED BY     G   ALTER SEQUENCE public.transports_id_seq OWNED BY public.transports.id;
          public          postgres    false    227            �           2604    16597 
   drivers id    DEFAULT     h   ALTER TABLE ONLY public.drivers ALTER COLUMN id SET DEFAULT nextval('public.drivers_id_seq'::regclass);
 9   ALTER TABLE public.drivers ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    215    214            �           2604    16598    end_points id    DEFAULT     n   ALTER TABLE ONLY public.end_points ALTER COLUMN id SET DEFAULT nextval('public.end_points_id_seq'::regclass);
 <   ALTER TABLE public.end_points ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    217    216            �           2604    16599    route_intervals id    DEFAULT     x   ALTER TABLE ONLY public.route_intervals ALTER COLUMN id SET DEFAULT nextval('public.route_intervals_id_seq'::regclass);
 A   ALTER TABLE public.route_intervals ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    219    218            �           2604    16600 	   routes id    DEFAULT     f   ALTER TABLE ONLY public.routes ALTER COLUMN id SET DEFAULT nextval('public.routes_id_seq'::regclass);
 8   ALTER TABLE public.routes ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    221    220            �           2604    16601    schedule id    DEFAULT     j   ALTER TABLE ONLY public.schedule ALTER COLUMN id SET DEFAULT nextval('public.schedule_id_seq'::regclass);
 :   ALTER TABLE public.schedule ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    223    222            �           2604    16602    transport_types id    DEFAULT     w   ALTER TABLE ONLY public.transport_types ALTER COLUMN id SET DEFAULT nextval('public.transport_type_id_seq'::regclass);
 A   ALTER TABLE public.transport_types ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    225    224            �           2604    16603    transports id    DEFAULT     n   ALTER TABLE ONLY public.transports ALTER COLUMN id SET DEFAULT nextval('public.transports_id_seq'::regclass);
 <   ALTER TABLE public.transports ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    227    226            4          0    16559    drivers 
   TABLE DATA           H   COPY public.drivers (id, first_name, last_name, patronymic) FROM stdin;
    public          postgres    false    214   �I       6          0    16565 
   end_points 
   TABLE DATA           4   COPY public.end_points (id, point_name) FROM stdin;
    public          postgres    false    216   �J       8          0    16571    route_intervals 
   TABLE DATA           <   COPY public.route_intervals (id, interval_name) FROM stdin;
    public          postgres    false    218   �J       :          0    16577    routes 
   TABLE DATA           �   COPY public.routes (id, route_number, route_start_point, route_finish_point, route_time, route_interval, cost_for_adult, cost_for_child) FROM stdin;
    public          postgres    false    220   �J       <          0    16581    schedule 
   TABLE DATA           @   COPY public.schedule (id, point, route, time_start) FROM stdin;
    public          postgres    false    222   �J       >          0    16585    transport_types 
   TABLE DATA           3   COPY public.transport_types (id, name) FROM stdin;
    public          postgres    false    224   �J       @          0    16591 
   transports 
   TABLE DATA           d   COPY public.transports (id, transport_type, gov_number, car_brand, seats_count, driver) FROM stdin;
    public          postgres    false    226   0K       O           0    0    drivers_id_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public.drivers_id_seq', 7, true);
          public          postgres    false    215            P           0    0    end_points_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.end_points_id_seq', 1, false);
          public          postgres    false    217            Q           0    0    route_intervals_id_seq    SEQUENCE SET     E   SELECT pg_catalog.setval('public.route_intervals_id_seq', 1, false);
          public          postgres    false    219            R           0    0    routes_id_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public.routes_id_seq', 1, false);
          public          postgres    false    221            S           0    0    schedule_id_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.schedule_id_seq', 1, false);
          public          postgres    false    223            T           0    0    transport_type_id_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public.transport_type_id_seq', 2, true);
          public          postgres    false    225            U           0    0    transports_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.transports_id_seq', 1, false);
          public          postgres    false    227            �           2606    16605    drivers drivers_pk 
   CONSTRAINT     P   ALTER TABLE ONLY public.drivers
    ADD CONSTRAINT drivers_pk PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.drivers DROP CONSTRAINT drivers_pk;
       public            postgres    false    214            �           2606    16607    end_points end_points_pk 
   CONSTRAINT     V   ALTER TABLE ONLY public.end_points
    ADD CONSTRAINT end_points_pk PRIMARY KEY (id);
 B   ALTER TABLE ONLY public.end_points DROP CONSTRAINT end_points_pk;
       public            postgres    false    216            �           2606    16609 "   route_intervals route_intervals_pk 
   CONSTRAINT     `   ALTER TABLE ONLY public.route_intervals
    ADD CONSTRAINT route_intervals_pk PRIMARY KEY (id);
 L   ALTER TABLE ONLY public.route_intervals DROP CONSTRAINT route_intervals_pk;
       public            postgres    false    218            �           2606    16611    routes routes_pk 
   CONSTRAINT     N   ALTER TABLE ONLY public.routes
    ADD CONSTRAINT routes_pk PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.routes DROP CONSTRAINT routes_pk;
       public            postgres    false    220            �           2606    16613    schedule schedule_pk 
   CONSTRAINT     R   ALTER TABLE ONLY public.schedule
    ADD CONSTRAINT schedule_pk PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.schedule DROP CONSTRAINT schedule_pk;
       public            postgres    false    222            �           2606    16615 !   transport_types transport_type_pk 
   CONSTRAINT     _   ALTER TABLE ONLY public.transport_types
    ADD CONSTRAINT transport_type_pk PRIMARY KEY (id);
 K   ALTER TABLE ONLY public.transport_types DROP CONSTRAINT transport_type_pk;
       public            postgres    false    224            �           2606    16617    transports transports_pk 
   CONSTRAINT     V   ALTER TABLE ONLY public.transports
    ADD CONSTRAINT transports_pk PRIMARY KEY (id);
 B   ALTER TABLE ONLY public.transports DROP CONSTRAINT transports_pk;
       public            postgres    false    226            �           1259    16618    drivers_id_uindex    INDEX     J   CREATE UNIQUE INDEX drivers_id_uindex ON public.drivers USING btree (id);
 %   DROP INDEX public.drivers_id_uindex;
       public            postgres    false    214            �           1259    16619    end_points_id_uindex    INDEX     P   CREATE UNIQUE INDEX end_points_id_uindex ON public.end_points USING btree (id);
 (   DROP INDEX public.end_points_id_uindex;
       public            postgres    false    216            �           1259    16620    route_intervals_id_uindex    INDEX     Z   CREATE UNIQUE INDEX route_intervals_id_uindex ON public.route_intervals USING btree (id);
 -   DROP INDEX public.route_intervals_id_uindex;
       public            postgres    false    218            �           1259    16621    routes_id_uindex    INDEX     H   CREATE UNIQUE INDEX routes_id_uindex ON public.routes USING btree (id);
 $   DROP INDEX public.routes_id_uindex;
       public            postgres    false    220            �           1259    16622    schedule_id_uindex    INDEX     L   CREATE UNIQUE INDEX schedule_id_uindex ON public.schedule USING btree (id);
 &   DROP INDEX public.schedule_id_uindex;
       public            postgres    false    222            �           1259    16623    transport_type_id_uindex    INDEX     Y   CREATE UNIQUE INDEX transport_type_id_uindex ON public.transport_types USING btree (id);
 ,   DROP INDEX public.transport_type_id_uindex;
       public            postgres    false    224            �           1259    16624    transports_id_uindex    INDEX     P   CREATE UNIQUE INDEX transports_id_uindex ON public.transports USING btree (id);
 (   DROP INDEX public.transports_id_uindex;
       public            postgres    false    226            �           2606    16625    routes routes_end_points_id_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public.routes
    ADD CONSTRAINT routes_end_points_id_fk FOREIGN KEY (route_start_point) REFERENCES public.end_points(id);
 H   ALTER TABLE ONLY public.routes DROP CONSTRAINT routes_end_points_id_fk;
       public          postgres    false    216    3215    220            �           2606    16630     routes routes_end_points_id_fk_2    FK CONSTRAINT     �   ALTER TABLE ONLY public.routes
    ADD CONSTRAINT routes_end_points_id_fk_2 FOREIGN KEY (route_finish_point) REFERENCES public.end_points(id);
 J   ALTER TABLE ONLY public.routes DROP CONSTRAINT routes_end_points_id_fk_2;
       public          postgres    false    3215    220    216            �           2606    16635 #   routes routes_route_intervals_id_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public.routes
    ADD CONSTRAINT routes_route_intervals_id_fk FOREIGN KEY (route_interval) REFERENCES public.route_intervals(id);
 M   ALTER TABLE ONLY public.routes DROP CONSTRAINT routes_route_intervals_id_fk;
       public          postgres    false    220    3218    218            �           2606    16640 "   schedule schedule_end_points_id_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public.schedule
    ADD CONSTRAINT schedule_end_points_id_fk FOREIGN KEY (point) REFERENCES public.end_points(id);
 L   ALTER TABLE ONLY public.schedule DROP CONSTRAINT schedule_end_points_id_fk;
       public          postgres    false    216    222    3215            �           2606    16645    schedule schedule_routes_id_fk    FK CONSTRAINT     |   ALTER TABLE ONLY public.schedule
    ADD CONSTRAINT schedule_routes_id_fk FOREIGN KEY (route) REFERENCES public.routes(id);
 H   ALTER TABLE ONLY public.schedule DROP CONSTRAINT schedule_routes_id_fk;
       public          postgres    false    220    222    3221            �           2606    16650 #   transports transports_drivers_id_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public.transports
    ADD CONSTRAINT transports_drivers_id_fk FOREIGN KEY (driver) REFERENCES public.drivers(id);
 M   ALTER TABLE ONLY public.transports DROP CONSTRAINT transports_drivers_id_fk;
       public          postgres    false    226    3212    214            �           2606    16655 +   transports transports_transport_types_id_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public.transports
    ADD CONSTRAINT transports_transport_types_id_fk FOREIGN KEY (transport_type) REFERENCES public.transport_types(id);
 U   ALTER TABLE ONLY public.transports DROP CONSTRAINT transports_transport_types_id_fk;
       public          postgres    false    226    3227    224            4   �   x���M�P�ׯ�1����A7n��ĝW �����FVHJHpѤ��L']:
�)�Wr2�� ���kǍR���rg2=�yYU?�lz�G(��af�g�0AH4��4d�X{��M���{����������_���S��      6      x������ � �      8      x������ � �      :      x������ � �      <      x������ � �      >   ,   x�3估��.6\�waÅM���Ƌ���8�b���� G�      @      x������ � �     