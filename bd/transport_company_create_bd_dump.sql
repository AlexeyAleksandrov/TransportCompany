PGDMP                         {            transport_company    14.5    14.5 C    8           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            9           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            :           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            ;           1262    27793    transport_company    DATABASE     n   CREATE DATABASE transport_company WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'Russian_Russia.1251';
 !   DROP DATABASE transport_company;
                postgres    false            �            1259    27820    drivers    TABLE     v   CREATE TABLE public.drivers (
    id bigint NOT NULL,
    first_name text,
    last_name text,
    patronymic text
);
    DROP TABLE public.drivers;
       public         heap    postgres    false            �            1259    27819    drivers_id_seq    SEQUENCE     w   CREATE SEQUENCE public.drivers_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public.drivers_id_seq;
       public          postgres    false    214            <           0    0    drivers_id_seq    SEQUENCE OWNED BY     A   ALTER SEQUENCE public.drivers_id_seq OWNED BY public.drivers.id;
          public          postgres    false    213            �            1259    27860 
   end_points    TABLE     P   CREATE TABLE public.end_points (
    id bigint NOT NULL,
    point_name text
);
    DROP TABLE public.end_points;
       public         heap    postgres    false            �            1259    27859    end_points_id_seq    SEQUENCE     z   CREATE SEQUENCE public.end_points_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE public.end_points_id_seq;
       public          postgres    false    220            =           0    0    end_points_id_seq    SEQUENCE OWNED BY     G   ALTER SEQUENCE public.end_points_id_seq OWNED BY public.end_points.id;
          public          postgres    false    219            �            1259    27845    route_intervals    TABLE     X   CREATE TABLE public.route_intervals (
    id bigint NOT NULL,
    interval_name text
);
 #   DROP TABLE public.route_intervals;
       public         heap    postgres    false            �            1259    27844    route_intervals_id_seq    SEQUENCE        CREATE SEQUENCE public.route_intervals_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 -   DROP SEQUENCE public.route_intervals_id_seq;
       public          postgres    false    218            >           0    0    route_intervals_id_seq    SEQUENCE OWNED BY     Q   ALTER SEQUENCE public.route_intervals_id_seq OWNED BY public.route_intervals.id;
          public          postgres    false    217            �            1259    27835    routes    TABLE       CREATE TABLE public.routes (
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
       public         heap    postgres    false            �            1259    27834    routes_id_seq    SEQUENCE     v   CREATE SEQUENCE public.routes_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 $   DROP SEQUENCE public.routes_id_seq;
       public          postgres    false    216            ?           0    0    routes_id_seq    SEQUENCE OWNED BY     ?   ALTER SEQUENCE public.routes_id_seq OWNED BY public.routes.id;
          public          postgres    false    215            �            1259    27892    schedule    TABLE     �   CREATE TABLE public.schedule (
    id bigint NOT NULL,
    point bigint,
    route bigint,
    time_start time without time zone
);
    DROP TABLE public.schedule;
       public         heap    postgres    false            �            1259    27891    schedule_id_seq    SEQUENCE     x   CREATE SEQUENCE public.schedule_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public.schedule_id_seq;
       public          postgres    false    222            @           0    0    schedule_id_seq    SEQUENCE OWNED BY     C   ALTER SEQUENCE public.schedule_id_seq OWNED BY public.schedule.id;
          public          postgres    false    221            �            1259    27795    transport_types    TABLE     X   CREATE TABLE public.transport_types (
    id bigint NOT NULL,
    name text NOT NULL
);
 #   DROP TABLE public.transport_types;
       public         heap    postgres    false            �            1259    27794    transport_type_id_seq    SEQUENCE     ~   CREATE SEQUENCE public.transport_type_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 ,   DROP SEQUENCE public.transport_type_id_seq;
       public          postgres    false    210            A           0    0    transport_type_id_seq    SEQUENCE OWNED BY     P   ALTER SEQUENCE public.transport_type_id_seq OWNED BY public.transport_types.id;
          public          postgres    false    209            �            1259    27805 
   transports    TABLE     �   CREATE TABLE public.transports (
    id bigint NOT NULL,
    transport_type bigint,
    gov_number character varying(9),
    car_brand text,
    seats_count integer,
    driver bigint
);
    DROP TABLE public.transports;
       public         heap    postgres    false            �            1259    27804    transports_id_seq    SEQUENCE     z   CREATE SEQUENCE public.transports_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE public.transports_id_seq;
       public          postgres    false    212            B           0    0    transports_id_seq    SEQUENCE OWNED BY     G   ALTER SEQUENCE public.transports_id_seq OWNED BY public.transports.id;
          public          postgres    false    211            |           2604    27823 
   drivers id    DEFAULT     h   ALTER TABLE ONLY public.drivers ALTER COLUMN id SET DEFAULT nextval('public.drivers_id_seq'::regclass);
 9   ALTER TABLE public.drivers ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    213    214    214                       2604    27863    end_points id    DEFAULT     n   ALTER TABLE ONLY public.end_points ALTER COLUMN id SET DEFAULT nextval('public.end_points_id_seq'::regclass);
 <   ALTER TABLE public.end_points ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    220    219    220            ~           2604    27848    route_intervals id    DEFAULT     x   ALTER TABLE ONLY public.route_intervals ALTER COLUMN id SET DEFAULT nextval('public.route_intervals_id_seq'::regclass);
 A   ALTER TABLE public.route_intervals ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    218    217    218            }           2604    27838 	   routes id    DEFAULT     f   ALTER TABLE ONLY public.routes ALTER COLUMN id SET DEFAULT nextval('public.routes_id_seq'::regclass);
 8   ALTER TABLE public.routes ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    215    216    216            �           2604    27895    schedule id    DEFAULT     j   ALTER TABLE ONLY public.schedule ALTER COLUMN id SET DEFAULT nextval('public.schedule_id_seq'::regclass);
 :   ALTER TABLE public.schedule ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    221    222    222            z           2604    27798    transport_types id    DEFAULT     w   ALTER TABLE ONLY public.transport_types ALTER COLUMN id SET DEFAULT nextval('public.transport_type_id_seq'::regclass);
 A   ALTER TABLE public.transport_types ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    210    209    210            {           2604    27808    transports id    DEFAULT     n   ALTER TABLE ONLY public.transports ALTER COLUMN id SET DEFAULT nextval('public.transports_id_seq'::regclass);
 <   ALTER TABLE public.transports ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    212    211    212            -          0    27820    drivers 
   TABLE DATA           H   COPY public.drivers (id, first_name, last_name, patronymic) FROM stdin;
    public          postgres    false    214   J       3          0    27860 
   end_points 
   TABLE DATA           4   COPY public.end_points (id, point_name) FROM stdin;
    public          postgres    false    220   #J       1          0    27845    route_intervals 
   TABLE DATA           <   COPY public.route_intervals (id, interval_name) FROM stdin;
    public          postgres    false    218   @J       /          0    27835    routes 
   TABLE DATA           �   COPY public.routes (id, route_number, route_start_point, route_finish_point, route_time, route_interval, cost_for_adult, cost_for_child) FROM stdin;
    public          postgres    false    216   ]J       5          0    27892    schedule 
   TABLE DATA           @   COPY public.schedule (id, point, route, time_start) FROM stdin;
    public          postgres    false    222   zJ       )          0    27795    transport_types 
   TABLE DATA           3   COPY public.transport_types (id, name) FROM stdin;
    public          postgres    false    210   �J       +          0    27805 
   transports 
   TABLE DATA           d   COPY public.transports (id, transport_type, gov_number, car_brand, seats_count, driver) FROM stdin;
    public          postgres    false    212   �J       C           0    0    drivers_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.drivers_id_seq', 1, false);
          public          postgres    false    213            D           0    0    end_points_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.end_points_id_seq', 1, false);
          public          postgres    false    219            E           0    0    route_intervals_id_seq    SEQUENCE SET     E   SELECT pg_catalog.setval('public.route_intervals_id_seq', 1, false);
          public          postgres    false    217            F           0    0    routes_id_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public.routes_id_seq', 1, false);
          public          postgres    false    215            G           0    0    schedule_id_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.schedule_id_seq', 1, false);
          public          postgres    false    221            H           0    0    transport_type_id_seq    SEQUENCE SET     D   SELECT pg_catalog.setval('public.transport_type_id_seq', 1, false);
          public          postgres    false    209            I           0    0    transports_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.transports_id_seq', 1, false);
          public          postgres    false    211            �           2606    27827    drivers drivers_pk 
   CONSTRAINT     P   ALTER TABLE ONLY public.drivers
    ADD CONSTRAINT drivers_pk PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.drivers DROP CONSTRAINT drivers_pk;
       public            postgres    false    214            �           2606    27867    end_points end_points_pk 
   CONSTRAINT     V   ALTER TABLE ONLY public.end_points
    ADD CONSTRAINT end_points_pk PRIMARY KEY (id);
 B   ALTER TABLE ONLY public.end_points DROP CONSTRAINT end_points_pk;
       public            postgres    false    220            �           2606    27852 "   route_intervals route_intervals_pk 
   CONSTRAINT     `   ALTER TABLE ONLY public.route_intervals
    ADD CONSTRAINT route_intervals_pk PRIMARY KEY (id);
 L   ALTER TABLE ONLY public.route_intervals DROP CONSTRAINT route_intervals_pk;
       public            postgres    false    218            �           2606    27842    routes routes_pk 
   CONSTRAINT     N   ALTER TABLE ONLY public.routes
    ADD CONSTRAINT routes_pk PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.routes DROP CONSTRAINT routes_pk;
       public            postgres    false    216            �           2606    27897    schedule schedule_pk 
   CONSTRAINT     R   ALTER TABLE ONLY public.schedule
    ADD CONSTRAINT schedule_pk PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.schedule DROP CONSTRAINT schedule_pk;
       public            postgres    false    222            �           2606    27802 !   transport_types transport_type_pk 
   CONSTRAINT     _   ALTER TABLE ONLY public.transport_types
    ADD CONSTRAINT transport_type_pk PRIMARY KEY (id);
 K   ALTER TABLE ONLY public.transport_types DROP CONSTRAINT transport_type_pk;
       public            postgres    false    210            �           2606    27812    transports transports_pk 
   CONSTRAINT     V   ALTER TABLE ONLY public.transports
    ADD CONSTRAINT transports_pk PRIMARY KEY (id);
 B   ALTER TABLE ONLY public.transports DROP CONSTRAINT transports_pk;
       public            postgres    false    212            �           1259    27828    drivers_id_uindex    INDEX     J   CREATE UNIQUE INDEX drivers_id_uindex ON public.drivers USING btree (id);
 %   DROP INDEX public.drivers_id_uindex;
       public            postgres    false    214            �           1259    27868    end_points_id_uindex    INDEX     P   CREATE UNIQUE INDEX end_points_id_uindex ON public.end_points USING btree (id);
 (   DROP INDEX public.end_points_id_uindex;
       public            postgres    false    220            �           1259    27853    route_intervals_id_uindex    INDEX     Z   CREATE UNIQUE INDEX route_intervals_id_uindex ON public.route_intervals USING btree (id);
 -   DROP INDEX public.route_intervals_id_uindex;
       public            postgres    false    218            �           1259    27843    routes_id_uindex    INDEX     H   CREATE UNIQUE INDEX routes_id_uindex ON public.routes USING btree (id);
 $   DROP INDEX public.routes_id_uindex;
       public            postgres    false    216            �           1259    27908    schedule_id_uindex    INDEX     L   CREATE UNIQUE INDEX schedule_id_uindex ON public.schedule USING btree (id);
 &   DROP INDEX public.schedule_id_uindex;
       public            postgres    false    222            �           1259    27803    transport_type_id_uindex    INDEX     Y   CREATE UNIQUE INDEX transport_type_id_uindex ON public.transport_types USING btree (id);
 ,   DROP INDEX public.transport_type_id_uindex;
       public            postgres    false    210            �           1259    27818    transports_id_uindex    INDEX     P   CREATE UNIQUE INDEX transports_id_uindex ON public.transports USING btree (id);
 (   DROP INDEX public.transports_id_uindex;
       public            postgres    false    212            �           2606    27881    routes routes_end_points_id_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public.routes
    ADD CONSTRAINT routes_end_points_id_fk FOREIGN KEY (route_start_point) REFERENCES public.end_points(id);
 H   ALTER TABLE ONLY public.routes DROP CONSTRAINT routes_end_points_id_fk;
       public          postgres    false    216    220    3218            �           2606    27886     routes routes_end_points_id_fk_2    FK CONSTRAINT     �   ALTER TABLE ONLY public.routes
    ADD CONSTRAINT routes_end_points_id_fk_2 FOREIGN KEY (route_finish_point) REFERENCES public.end_points(id);
 J   ALTER TABLE ONLY public.routes DROP CONSTRAINT routes_end_points_id_fk_2;
       public          postgres    false    3218    220    216            �           2606    27854 #   routes routes_route_intervals_id_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public.routes
    ADD CONSTRAINT routes_route_intervals_id_fk FOREIGN KEY (route_interval) REFERENCES public.route_intervals(id);
 M   ALTER TABLE ONLY public.routes DROP CONSTRAINT routes_route_intervals_id_fk;
       public          postgres    false    218    216    3215            �           2606    27898 "   schedule schedule_end_points_id_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public.schedule
    ADD CONSTRAINT schedule_end_points_id_fk FOREIGN KEY (point) REFERENCES public.end_points(id);
 L   ALTER TABLE ONLY public.schedule DROP CONSTRAINT schedule_end_points_id_fk;
       public          postgres    false    3218    222    220            �           2606    27903    schedule schedule_routes_id_fk    FK CONSTRAINT     |   ALTER TABLE ONLY public.schedule
    ADD CONSTRAINT schedule_routes_id_fk FOREIGN KEY (route) REFERENCES public.routes(id);
 H   ALTER TABLE ONLY public.schedule DROP CONSTRAINT schedule_routes_id_fk;
       public          postgres    false    222    3212    216            �           2606    27829 #   transports transports_drivers_id_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public.transports
    ADD CONSTRAINT transports_drivers_id_fk FOREIGN KEY (driver) REFERENCES public.drivers(id);
 M   ALTER TABLE ONLY public.transports DROP CONSTRAINT transports_drivers_id_fk;
       public          postgres    false    214    3209    212            �           2606    27813 +   transports transports_transport_types_id_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public.transports
    ADD CONSTRAINT transports_transport_types_id_fk FOREIGN KEY (transport_type) REFERENCES public.transport_types(id);
 U   ALTER TABLE ONLY public.transports DROP CONSTRAINT transports_transport_types_id_fk;
       public          postgres    false    212    3203    210            -      x������ � �      3      x������ � �      1      x������ � �      /      x������ � �      5      x������ � �      )      x������ � �      +      x������ � �     