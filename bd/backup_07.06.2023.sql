PGDMP     $                    {            transport_company    15.2    15.2 W    e           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            f           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            g           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            h           1262    16558    transport_company    DATABASE     �   CREATE DATABASE transport_company WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Russian_Russia.1251';
 !   DROP DATABASE transport_company;
                postgres    false            �            1255    17010    check_gov_number()    FUNCTION     \  CREATE FUNCTION public.check_gov_number() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    IF NEW.gov_number NOT SIMILAR TO '[а-я]{1}\d{3}[а-я]{2}\d{2,3}' THEN
        RAISE EXCEPTION 'Гос. номер должен соответствовать формату: WDDDWWDD или WDDDWWDDD';
    END IF;
    RETURN NEW;
END;
$$;
 )   DROP FUNCTION public.check_gov_number();
       public          postgres    false            �            1259    16752    drivers    TABLE     �   CREATE TABLE public.drivers (
    id bigint NOT NULL,
    first_name character varying(255),
    last_name character varying(255),
    patronymic character varying(255)
);
    DROP TABLE public.drivers;
       public         heap    postgres    false            �            1259    16757    drivers_id_seq    SEQUENCE     w   CREATE SEQUENCE public.drivers_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public.drivers_id_seq;
       public          postgres    false    214            i           0    0    drivers_id_seq    SEQUENCE OWNED BY     A   ALTER SEQUENCE public.drivers_id_seq OWNED BY public.drivers.id;
          public          postgres    false    215            �            1259    16758 
   end_points    TABLE     b   CREATE TABLE public.end_points (
    id bigint NOT NULL,
    point_name character varying(255)
);
    DROP TABLE public.end_points;
       public         heap    postgres    false            �            1259    16763    end_points_id_seq    SEQUENCE     z   CREATE SEQUENCE public.end_points_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE public.end_points_id_seq;
       public          postgres    false    216            j           0    0    end_points_id_seq    SEQUENCE OWNED BY     G   ALTER SEQUENCE public.end_points_id_seq OWNED BY public.end_points.id;
          public          postgres    false    217            �            1259    16764    route_intervals    TABLE     j   CREATE TABLE public.route_intervals (
    id bigint NOT NULL,
    interval_name character varying(255)
);
 #   DROP TABLE public.route_intervals;
       public         heap    postgres    false            �            1259    16769    route_intervals_id_seq    SEQUENCE        CREATE SEQUENCE public.route_intervals_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 -   DROP SEQUENCE public.route_intervals_id_seq;
       public          postgres    false    218            k           0    0    route_intervals_id_seq    SEQUENCE OWNED BY     Q   ALTER SEQUENCE public.route_intervals_id_seq OWNED BY public.route_intervals.id;
          public          postgres    false    219            �            1259    16770    routes    TABLE     1  CREATE TABLE public.routes (
    id bigint NOT NULL,
    route_number character varying(4),
    route_start_point bigint,
    route_finish_point bigint,
    route_time integer,
    route_interval bigint,
    cost_for_adult integer,
    cost_for_child integer,
    CONSTRAINT check_route_cost_for_adult CHECK ((cost_for_adult >= 0)),
    CONSTRAINT check_route_cost_for_child CHECK ((cost_for_child >= 0)),
    CONSTRAINT check_route_cost_for_child_to_adult CHECK ((cost_for_child <= cost_for_adult)),
    CONSTRAINT check_route_time CHECK ((route_time > 0))
);
    DROP TABLE public.routes;
       public         heap    postgres    false            �            1259    16773    routes_id_seq    SEQUENCE     v   CREATE SEQUENCE public.routes_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 $   DROP SEQUENCE public.routes_id_seq;
       public          postgres    false    220            l           0    0    routes_id_seq    SEQUENCE OWNED BY     ?   ALTER SEQUENCE public.routes_id_seq OWNED BY public.routes.id;
          public          postgres    false    221            �            1259    16774    schedule    TABLE     �   CREATE TABLE public.schedule (
    id bigint NOT NULL,
    route bigint,
    time_start time without time zone,
    transport bigint,
    date date NOT NULL
);
    DROP TABLE public.schedule;
       public         heap    postgres    false            �            1259    16777    schedule_id_seq    SEQUENCE     x   CREATE SEQUENCE public.schedule_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public.schedule_id_seq;
       public          postgres    false    222            m           0    0    schedule_id_seq    SEQUENCE OWNED BY     C   ALTER SEQUENCE public.schedule_id_seq OWNED BY public.schedule.id;
          public          postgres    false    223            �            1259    16778    tickets    TABLE     a   CREATE TABLE public.tickets (
    id bigint NOT NULL,
    schedule bigint,
    user_id bigint
);
    DROP TABLE public.tickets;
       public         heap    postgres    false            �            1259    16781    tickets_id_seq    SEQUENCE     w   CREATE SEQUENCE public.tickets_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public.tickets_id_seq;
       public          postgres    false    224            n           0    0    tickets_id_seq    SEQUENCE OWNED BY     A   ALTER SEQUENCE public.tickets_id_seq OWNED BY public.tickets.id;
          public          postgres    false    225            �            1259    16782    transport_types    TABLE     j   CREATE TABLE public.transport_types (
    id bigint NOT NULL,
    name character varying(255) NOT NULL
);
 #   DROP TABLE public.transport_types;
       public         heap    postgres    false            �            1259    16787    transport_type_id_seq    SEQUENCE     ~   CREATE SEQUENCE public.transport_type_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 ,   DROP SEQUENCE public.transport_type_id_seq;
       public          postgres    false    226            o           0    0    transport_type_id_seq    SEQUENCE OWNED BY     P   ALTER SEQUENCE public.transport_type_id_seq OWNED BY public.transport_types.id;
          public          postgres    false    227            �            1259    16788 
   transports    TABLE     �   CREATE TABLE public.transports (
    id bigint NOT NULL,
    transport_type bigint,
    gov_number character varying(9),
    car_brand character varying(255),
    seats_count integer,
    driver bigint
);
    DROP TABLE public.transports;
       public         heap    postgres    false            �            1259    16793    transports_id_seq    SEQUENCE     z   CREATE SEQUENCE public.transports_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE public.transports_id_seq;
       public          postgres    false    228            p           0    0    transports_id_seq    SEQUENCE OWNED BY     G   ALTER SEQUENCE public.transports_id_seq OWNED BY public.transports.id;
          public          postgres    false    229            �            1259    16794    users    TABLE     �  CREATE TABLE public.users (
    id bigint NOT NULL,
    username character varying(255) NOT NULL,
    firstname character varying(255) DEFAULT ''::character varying NOT NULL,
    surname character varying(255) DEFAULT ''::character varying NOT NULL,
    patronymic character varying(255) DEFAULT ''::character varying NOT NULL,
    passwd character varying(255) NOT NULL,
    status character varying(255)
);
    DROP TABLE public.users;
       public         heap    postgres    false            �            1259    16799    users_id_seq    SEQUENCE     u   CREATE SEQUENCE public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.users_id_seq;
       public          postgres    false    230            q           0    0    users_id_seq    SEQUENCE OWNED BY     =   ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;
          public          postgres    false    231            �           2604    16800 
   drivers id    DEFAULT     h   ALTER TABLE ONLY public.drivers ALTER COLUMN id SET DEFAULT nextval('public.drivers_id_seq'::regclass);
 9   ALTER TABLE public.drivers ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    215    214            �           2604    16801    end_points id    DEFAULT     n   ALTER TABLE ONLY public.end_points ALTER COLUMN id SET DEFAULT nextval('public.end_points_id_seq'::regclass);
 <   ALTER TABLE public.end_points ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    217    216            �           2604    16802    route_intervals id    DEFAULT     x   ALTER TABLE ONLY public.route_intervals ALTER COLUMN id SET DEFAULT nextval('public.route_intervals_id_seq'::regclass);
 A   ALTER TABLE public.route_intervals ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    219    218            �           2604    16803 	   routes id    DEFAULT     f   ALTER TABLE ONLY public.routes ALTER COLUMN id SET DEFAULT nextval('public.routes_id_seq'::regclass);
 8   ALTER TABLE public.routes ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    221    220            �           2604    16804    schedule id    DEFAULT     j   ALTER TABLE ONLY public.schedule ALTER COLUMN id SET DEFAULT nextval('public.schedule_id_seq'::regclass);
 :   ALTER TABLE public.schedule ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    223    222            �           2604    16805 
   tickets id    DEFAULT     h   ALTER TABLE ONLY public.tickets ALTER COLUMN id SET DEFAULT nextval('public.tickets_id_seq'::regclass);
 9   ALTER TABLE public.tickets ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    225    224            �           2604    16806    transport_types id    DEFAULT     w   ALTER TABLE ONLY public.transport_types ALTER COLUMN id SET DEFAULT nextval('public.transport_type_id_seq'::regclass);
 A   ALTER TABLE public.transport_types ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    227    226            �           2604    16807    transports id    DEFAULT     n   ALTER TABLE ONLY public.transports ALTER COLUMN id SET DEFAULT nextval('public.transports_id_seq'::regclass);
 <   ALTER TABLE public.transports ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    229    228            �           2604    16808    users id    DEFAULT     d   ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);
 7   ALTER TABLE public.users ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    231    230            Q          0    16752    drivers 
   TABLE DATA           H   COPY public.drivers (id, first_name, last_name, patronymic) FROM stdin;
    public          postgres    false    214   �b       S          0    16758 
   end_points 
   TABLE DATA           4   COPY public.end_points (id, point_name) FROM stdin;
    public          postgres    false    216   �c       U          0    16764    route_intervals 
   TABLE DATA           <   COPY public.route_intervals (id, interval_name) FROM stdin;
    public          postgres    false    218   sd       W          0    16770    routes 
   TABLE DATA           �   COPY public.routes (id, route_number, route_start_point, route_finish_point, route_time, route_interval, cost_for_adult, cost_for_child) FROM stdin;
    public          postgres    false    220   �d       Y          0    16774    schedule 
   TABLE DATA           J   COPY public.schedule (id, route, time_start, transport, date) FROM stdin;
    public          postgres    false    222   Ae       [          0    16778    tickets 
   TABLE DATA           8   COPY public.tickets (id, schedule, user_id) FROM stdin;
    public          postgres    false    224   �e       ]          0    16782    transport_types 
   TABLE DATA           3   COPY public.transport_types (id, name) FROM stdin;
    public          postgres    false    226   �e       _          0    16788 
   transports 
   TABLE DATA           d   COPY public.transports (id, transport_type, gov_number, car_brand, seats_count, driver) FROM stdin;
    public          postgres    false    228   +f       a          0    16794    users 
   TABLE DATA           ]   COPY public.users (id, username, firstname, surname, patronymic, passwd, status) FROM stdin;
    public          postgres    false    230   �f       r           0    0    drivers_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.drivers_id_seq', 12, true);
          public          postgres    false    215            s           0    0    end_points_id_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public.end_points_id_seq', 9, true);
          public          postgres    false    217            t           0    0    route_intervals_id_seq    SEQUENCE SET     D   SELECT pg_catalog.setval('public.route_intervals_id_seq', 5, true);
          public          postgres    false    219            u           0    0    routes_id_seq    SEQUENCE SET     ;   SELECT pg_catalog.setval('public.routes_id_seq', 6, true);
          public          postgres    false    221            v           0    0    schedule_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.schedule_id_seq', 8, true);
          public          postgres    false    223            w           0    0    tickets_id_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public.tickets_id_seq', 3, true);
          public          postgres    false    225            x           0    0    transport_type_id_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public.transport_type_id_seq', 8, true);
          public          postgres    false    227            y           0    0    transports_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.transports_id_seq', 21, true);
          public          postgres    false    229            z           0    0    users_id_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.users_id_seq', 2, true);
          public          postgres    false    231            �           2606    16810    drivers drivers_pk 
   CONSTRAINT     P   ALTER TABLE ONLY public.drivers
    ADD CONSTRAINT drivers_pk PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.drivers DROP CONSTRAINT drivers_pk;
       public            postgres    false    214            �           2606    16812    end_points end_points_pk 
   CONSTRAINT     V   ALTER TABLE ONLY public.end_points
    ADD CONSTRAINT end_points_pk PRIMARY KEY (id);
 B   ALTER TABLE ONLY public.end_points DROP CONSTRAINT end_points_pk;
       public            postgres    false    216            �           2606    16814 "   route_intervals route_intervals_pk 
   CONSTRAINT     `   ALTER TABLE ONLY public.route_intervals
    ADD CONSTRAINT route_intervals_pk PRIMARY KEY (id);
 L   ALTER TABLE ONLY public.route_intervals DROP CONSTRAINT route_intervals_pk;
       public            postgres    false    218            �           2606    16816    routes routes_pk 
   CONSTRAINT     N   ALTER TABLE ONLY public.routes
    ADD CONSTRAINT routes_pk PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.routes DROP CONSTRAINT routes_pk;
       public            postgres    false    220            �           2606    16818    schedule schedule_pk 
   CONSTRAINT     R   ALTER TABLE ONLY public.schedule
    ADD CONSTRAINT schedule_pk PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.schedule DROP CONSTRAINT schedule_pk;
       public            postgres    false    222            �           2606    16820    tickets tickets_pk 
   CONSTRAINT     P   ALTER TABLE ONLY public.tickets
    ADD CONSTRAINT tickets_pk PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.tickets DROP CONSTRAINT tickets_pk;
       public            postgres    false    224            �           2606    16822 !   transport_types transport_type_pk 
   CONSTRAINT     _   ALTER TABLE ONLY public.transport_types
    ADD CONSTRAINT transport_type_pk PRIMARY KEY (id);
 K   ALTER TABLE ONLY public.transport_types DROP CONSTRAINT transport_type_pk;
       public            postgres    false    226            �           2606    16824    transports transports_pk 
   CONSTRAINT     V   ALTER TABLE ONLY public.transports
    ADD CONSTRAINT transports_pk PRIMARY KEY (id);
 B   ALTER TABLE ONLY public.transports DROP CONSTRAINT transports_pk;
       public            postgres    false    228            �           2606    16826    users users_pk 
   CONSTRAINT     L   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pk PRIMARY KEY (id);
 8   ALTER TABLE ONLY public.users DROP CONSTRAINT users_pk;
       public            postgres    false    230            �           1259    16827    drivers_id_uindex    INDEX     J   CREATE UNIQUE INDEX drivers_id_uindex ON public.drivers USING btree (id);
 %   DROP INDEX public.drivers_id_uindex;
       public            postgres    false    214            �           1259    16828    end_points_id_uindex    INDEX     P   CREATE UNIQUE INDEX end_points_id_uindex ON public.end_points USING btree (id);
 (   DROP INDEX public.end_points_id_uindex;
       public            postgres    false    216            �           1259    16829    route_intervals_id_uindex    INDEX     Z   CREATE UNIQUE INDEX route_intervals_id_uindex ON public.route_intervals USING btree (id);
 -   DROP INDEX public.route_intervals_id_uindex;
       public            postgres    false    218            �           1259    16830    routes_id_uindex    INDEX     H   CREATE UNIQUE INDEX routes_id_uindex ON public.routes USING btree (id);
 $   DROP INDEX public.routes_id_uindex;
       public            postgres    false    220            �           1259    16831    schedule_id_uindex    INDEX     L   CREATE UNIQUE INDEX schedule_id_uindex ON public.schedule USING btree (id);
 &   DROP INDEX public.schedule_id_uindex;
       public            postgres    false    222            �           1259    16832    tickets_id_uindex    INDEX     J   CREATE UNIQUE INDEX tickets_id_uindex ON public.tickets USING btree (id);
 %   DROP INDEX public.tickets_id_uindex;
       public            postgres    false    224            �           1259    16833    transport_type_id_uindex    INDEX     Y   CREATE UNIQUE INDEX transport_type_id_uindex ON public.transport_types USING btree (id);
 ,   DROP INDEX public.transport_type_id_uindex;
       public            postgres    false    226            �           1259    16834    transports_id_uindex    INDEX     P   CREATE UNIQUE INDEX transports_id_uindex ON public.transports USING btree (id);
 (   DROP INDEX public.transports_id_uindex;
       public            postgres    false    228            �           1259    16835    users_id_uindex    INDEX     F   CREATE UNIQUE INDEX users_id_uindex ON public.users USING btree (id);
 #   DROP INDEX public.users_id_uindex;
       public            postgres    false    230            �           1259    16936    users_username_uindex    INDEX     R   CREATE UNIQUE INDEX users_username_uindex ON public.users USING btree (username);
 )   DROP INDEX public.users_username_uindex;
       public            postgres    false    230            �           2620    17011 #   transports check_gov_number_trigger    TRIGGER     �   CREATE TRIGGER check_gov_number_trigger BEFORE INSERT OR UPDATE ON public.transports FOR EACH ROW EXECUTE FUNCTION public.check_gov_number();
 <   DROP TRIGGER check_gov_number_trigger ON public.transports;
       public          postgres    false    228    232            �           2606    16837    routes routes_end_points_id_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public.routes
    ADD CONSTRAINT routes_end_points_id_fk FOREIGN KEY (route_start_point) REFERENCES public.end_points(id);
 H   ALTER TABLE ONLY public.routes DROP CONSTRAINT routes_end_points_id_fk;
       public          postgres    false    216    220    3235            �           2606    16842     routes routes_end_points_id_fk_2    FK CONSTRAINT     �   ALTER TABLE ONLY public.routes
    ADD CONSTRAINT routes_end_points_id_fk_2 FOREIGN KEY (route_finish_point) REFERENCES public.end_points(id);
 J   ALTER TABLE ONLY public.routes DROP CONSTRAINT routes_end_points_id_fk_2;
       public          postgres    false    3235    220    216            �           2606    16847 #   routes routes_route_intervals_id_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public.routes
    ADD CONSTRAINT routes_route_intervals_id_fk FOREIGN KEY (route_interval) REFERENCES public.route_intervals(id);
 M   ALTER TABLE ONLY public.routes DROP CONSTRAINT routes_route_intervals_id_fk;
       public          postgres    false    3238    218    220            �           2606    16852    schedule schedule_routes_id_fk    FK CONSTRAINT     |   ALTER TABLE ONLY public.schedule
    ADD CONSTRAINT schedule_routes_id_fk FOREIGN KEY (route) REFERENCES public.routes(id);
 H   ALTER TABLE ONLY public.schedule DROP CONSTRAINT schedule_routes_id_fk;
       public          postgres    false    220    3241    222            �           2606    16857 "   schedule schedule_transports_id_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public.schedule
    ADD CONSTRAINT schedule_transports_id_fk FOREIGN KEY (transport) REFERENCES public.transports(id);
 L   ALTER TABLE ONLY public.schedule DROP CONSTRAINT schedule_transports_id_fk;
       public          postgres    false    3253    222    228            �           2606    16862    tickets tickets_schedule_id_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public.tickets
    ADD CONSTRAINT tickets_schedule_id_fk FOREIGN KEY (schedule) REFERENCES public.schedule(id);
 H   ALTER TABLE ONLY public.tickets DROP CONSTRAINT tickets_schedule_id_fk;
       public          postgres    false    224    3244    222            �           2606    16867 #   transports transports_drivers_id_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public.transports
    ADD CONSTRAINT transports_drivers_id_fk FOREIGN KEY (driver) REFERENCES public.drivers(id);
 M   ALTER TABLE ONLY public.transports DROP CONSTRAINT transports_drivers_id_fk;
       public          postgres    false    214    3232    228            �           2606    16872 +   transports transports_transport_types_id_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public.transports
    ADD CONSTRAINT transports_transport_types_id_fk FOREIGN KEY (transport_type) REFERENCES public.transport_types(id);
 U   ALTER TABLE ONLY public.transports DROP CONSTRAINT transports_transport_types_id_fk;
       public          postgres    false    226    3250    228            Q   �   x���9�0Dk�0H	�]8L�

@ ��
V����o��d�E�p��o�/c�VV��C�p��%J�|��W��Ȑâ��)]:�z��s��0��}Q�FGId��^�
f�p�~Ϟ�����e�qZW��4����l�4��g0���]��(_���A�(vzM�`���V��Z���w��      S   �   x�m�;
�@��S,9@�����$
Z(jl����Dq5��
����$bc1��?�a:
x�,�W<돡��N�R'PO���~�[�+DH��A�Q�>�-�h�?�=,����MN4D�+d�ۀ�F
q�PH4��x9Y�ѣ���+P��L�ka��y.��HhҜpv�/AS��>��i      U   ^   x�3漰��>�{/l��~q�Ŧ{/v_��e�ya�m@�$ua���eQ|��/��~aP�)ԈM�/�^��6����=... ƓF�      W   P   x����@�jg���.t�H��
7��ɖ���ҞUqo[[��-��R�yW�S���M�yͧv�C�A(�r,"�1�      Y   Q   x�m���0Cѳ��I��t���B9	ɷ�/<:9����،R��wͯh�
�m\�n����J"�|�X�͟)�ʹ��p-�      [      x�3�4���2�4Q1z\\\ �      ]   Q   x�3估��.6\�waÅM���Ƌ���8�L9/6���­vB�� ��m�;��9/��U삪������� �D�      _   �   x�}�K�0D��]���N�]8�+$K�CD@UT>g�g˂��'��3��tºՁ�%���{L��N�oݓ-��?�<qC��H�PX�]-��Ux�N����`�η�\�DXXR}��.(x��k��,	E8��)�?�B�7+����^�      a   �   x�M̻�0 Й~3�"XGh0X��K)��y-*0��렉�6�n^01>L�LL�v ���Xǭ�=2?۴�j�S$��*��u�s9A���8����z�7|M�G�����E�5Ɂm�[7/�~��D]�-~���Ŀ�b!�ޖ�/     