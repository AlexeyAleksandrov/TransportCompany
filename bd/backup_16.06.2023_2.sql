PGDMP     #    1                {            transport_company    14.5    14.5 Y    ^           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            _           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            `           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            a           1262    28373    transport_company    DATABASE     n   CREATE DATABASE transport_company WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'Russian_Russia.1251';
 !   DROP DATABASE transport_company;
                postgres    false            �            1255    28374    check_gov_number()    FUNCTION     \  CREATE FUNCTION public.check_gov_number() RETURNS trigger
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
       public          postgres    false            �            1255    28375    check_tickets_count()    FUNCTION     �  CREATE FUNCTION public.check_tickets_count() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    IF(SELECT isgt
               FROM (SELECT COUNT(*) AS cnt, (COUNT(*) >= t2.seats_count) AS isgt, t.schedule AS sched, route_number, schedule.date, seats_count
        FROM schedule
        JOIN tickets t ON schedule.id = t.schedule
        JOIN transports t2 ON t2.id = schedule.transport
        JOIN routes r ON r.id = schedule.route
                                GROUP BY t.schedule, schedule.date, route_number, t2.seats_count) as stt2r
        WHERE sched = new.schedule) THEN
        RAISE EXCEPTION 'На данный рейс продано максимальное кол-во билетов!';
    END IF;
    RETURN NEW;
END;
$$;
 ,   DROP FUNCTION public.check_tickets_count();
       public          postgres    false            �            1259    28376    drivers    TABLE     �   CREATE TABLE public.drivers (
    id bigint NOT NULL,
    first_name character varying(255),
    last_name character varying(255),
    patronymic character varying(255)
);
    DROP TABLE public.drivers;
       public         heap    postgres    false            �            1259    28381    drivers_id_seq    SEQUENCE     w   CREATE SEQUENCE public.drivers_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public.drivers_id_seq;
       public          postgres    false    209            b           0    0    drivers_id_seq    SEQUENCE OWNED BY     A   ALTER SEQUENCE public.drivers_id_seq OWNED BY public.drivers.id;
          public          postgres    false    210            �            1259    28382 
   end_points    TABLE     b   CREATE TABLE public.end_points (
    id bigint NOT NULL,
    point_name character varying(255)
);
    DROP TABLE public.end_points;
       public         heap    postgres    false            �            1259    28385    end_points_id_seq    SEQUENCE     z   CREATE SEQUENCE public.end_points_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE public.end_points_id_seq;
       public          postgres    false    211            c           0    0    end_points_id_seq    SEQUENCE OWNED BY     G   ALTER SEQUENCE public.end_points_id_seq OWNED BY public.end_points.id;
          public          postgres    false    212            �            1259    28386    route_intervals    TABLE     j   CREATE TABLE public.route_intervals (
    id bigint NOT NULL,
    interval_name character varying(255)
);
 #   DROP TABLE public.route_intervals;
       public         heap    postgres    false            �            1259    28389    route_intervals_id_seq    SEQUENCE        CREATE SEQUENCE public.route_intervals_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 -   DROP SEQUENCE public.route_intervals_id_seq;
       public          postgres    false    213            d           0    0    route_intervals_id_seq    SEQUENCE OWNED BY     Q   ALTER SEQUENCE public.route_intervals_id_seq OWNED BY public.route_intervals.id;
          public          postgres    false    214            �            1259    28390    routes    TABLE     1  CREATE TABLE public.routes (
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
       public         heap    postgres    false            �            1259    28397    routes_id_seq    SEQUENCE     v   CREATE SEQUENCE public.routes_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 $   DROP SEQUENCE public.routes_id_seq;
       public          postgres    false    215            e           0    0    routes_id_seq    SEQUENCE OWNED BY     ?   ALTER SEQUENCE public.routes_id_seq OWNED BY public.routes.id;
          public          postgres    false    216            �            1259    28398    schedule    TABLE     �   CREATE TABLE public.schedule (
    id bigint NOT NULL,
    route bigint,
    time_start time without time zone,
    transport bigint,
    date date NOT NULL,
    direction integer DEFAULT 0
);
    DROP TABLE public.schedule;
       public         heap    postgres    false            �            1259    28402    schedule_id_seq    SEQUENCE     x   CREATE SEQUENCE public.schedule_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public.schedule_id_seq;
       public          postgres    false    217            f           0    0    schedule_id_seq    SEQUENCE OWNED BY     C   ALTER SEQUENCE public.schedule_id_seq OWNED BY public.schedule.id;
          public          postgres    false    218            �            1259    28403    tickets    TABLE     }   CREATE TABLE public.tickets (
    id bigint NOT NULL,
    schedule bigint,
    user_id bigint,
    type integer DEFAULT 0
);
    DROP TABLE public.tickets;
       public         heap    postgres    false            �            1259    28407    tickets_id_seq    SEQUENCE     w   CREATE SEQUENCE public.tickets_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public.tickets_id_seq;
       public          postgres    false    219            g           0    0    tickets_id_seq    SEQUENCE OWNED BY     A   ALTER SEQUENCE public.tickets_id_seq OWNED BY public.tickets.id;
          public          postgres    false    220            �            1259    28408    transport_types    TABLE     j   CREATE TABLE public.transport_types (
    id bigint NOT NULL,
    name character varying(255) NOT NULL
);
 #   DROP TABLE public.transport_types;
       public         heap    postgres    false            �            1259    28411    transport_type_id_seq    SEQUENCE     ~   CREATE SEQUENCE public.transport_type_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 ,   DROP SEQUENCE public.transport_type_id_seq;
       public          postgres    false    221            h           0    0    transport_type_id_seq    SEQUENCE OWNED BY     P   ALTER SEQUENCE public.transport_type_id_seq OWNED BY public.transport_types.id;
          public          postgres    false    222            �            1259    28412 
   transports    TABLE     �   CREATE TABLE public.transports (
    id bigint NOT NULL,
    transport_type bigint,
    gov_number character varying(9),
    car_brand character varying(255),
    seats_count integer,
    driver bigint
);
    DROP TABLE public.transports;
       public         heap    postgres    false            �            1259    28415    transports_id_seq    SEQUENCE     z   CREATE SEQUENCE public.transports_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE public.transports_id_seq;
       public          postgres    false    223            i           0    0    transports_id_seq    SEQUENCE OWNED BY     G   ALTER SEQUENCE public.transports_id_seq OWNED BY public.transports.id;
          public          postgres    false    224            �            1259    28416    users    TABLE     �  CREATE TABLE public.users (
    id bigint NOT NULL,
    username character varying(255) NOT NULL,
    firstname character varying(255) DEFAULT ''::character varying NOT NULL,
    surname character varying(255) DEFAULT ''::character varying NOT NULL,
    patronymic character varying(255) DEFAULT ''::character varying NOT NULL,
    passwd character varying(255) NOT NULL,
    status character varying(255),
    role character varying(255) DEFAULT 'USER'::character varying
);
    DROP TABLE public.users;
       public         heap    postgres    false            �            1259    28424    users_id_seq    SEQUENCE     u   CREATE SEQUENCE public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.users_id_seq;
       public          postgres    false    225            j           0    0    users_id_seq    SEQUENCE OWNED BY     =   ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;
          public          postgres    false    226            �           2604    28425 
   drivers id    DEFAULT     h   ALTER TABLE ONLY public.drivers ALTER COLUMN id SET DEFAULT nextval('public.drivers_id_seq'::regclass);
 9   ALTER TABLE public.drivers ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    210    209            �           2604    28426    end_points id    DEFAULT     n   ALTER TABLE ONLY public.end_points ALTER COLUMN id SET DEFAULT nextval('public.end_points_id_seq'::regclass);
 <   ALTER TABLE public.end_points ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    212    211            �           2604    28427    route_intervals id    DEFAULT     x   ALTER TABLE ONLY public.route_intervals ALTER COLUMN id SET DEFAULT nextval('public.route_intervals_id_seq'::regclass);
 A   ALTER TABLE public.route_intervals ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    214    213            �           2604    28428 	   routes id    DEFAULT     f   ALTER TABLE ONLY public.routes ALTER COLUMN id SET DEFAULT nextval('public.routes_id_seq'::regclass);
 8   ALTER TABLE public.routes ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    216    215            �           2604    28429    schedule id    DEFAULT     j   ALTER TABLE ONLY public.schedule ALTER COLUMN id SET DEFAULT nextval('public.schedule_id_seq'::regclass);
 :   ALTER TABLE public.schedule ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    218    217            �           2604    28430 
   tickets id    DEFAULT     h   ALTER TABLE ONLY public.tickets ALTER COLUMN id SET DEFAULT nextval('public.tickets_id_seq'::regclass);
 9   ALTER TABLE public.tickets ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    220    219            �           2604    28431    transport_types id    DEFAULT     w   ALTER TABLE ONLY public.transport_types ALTER COLUMN id SET DEFAULT nextval('public.transport_type_id_seq'::regclass);
 A   ALTER TABLE public.transport_types ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    222    221            �           2604    28432    transports id    DEFAULT     n   ALTER TABLE ONLY public.transports ALTER COLUMN id SET DEFAULT nextval('public.transports_id_seq'::regclass);
 <   ALTER TABLE public.transports ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    224    223            �           2604    28433    users id    DEFAULT     d   ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);
 7   ALTER TABLE public.users ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    226    225            J          0    28376    drivers 
   TABLE DATA           H   COPY public.drivers (id, first_name, last_name, patronymic) FROM stdin;
    public          postgres    false    209   �h       L          0    28382 
   end_points 
   TABLE DATA           4   COPY public.end_points (id, point_name) FROM stdin;
    public          postgres    false    211   Wi       N          0    28386    route_intervals 
   TABLE DATA           <   COPY public.route_intervals (id, interval_name) FROM stdin;
    public          postgres    false    213   j       P          0    28390    routes 
   TABLE DATA           �   COPY public.routes (id, route_number, route_start_point, route_finish_point, route_time, route_interval, cost_for_adult, cost_for_child) FROM stdin;
    public          postgres    false    215   �j       R          0    28398    schedule 
   TABLE DATA           U   COPY public.schedule (id, route, time_start, transport, date, direction) FROM stdin;
    public          postgres    false    217   �j       T          0    28403    tickets 
   TABLE DATA           >   COPY public.tickets (id, schedule, user_id, type) FROM stdin;
    public          postgres    false    219   �k       V          0    28408    transport_types 
   TABLE DATA           3   COPY public.transport_types (id, name) FROM stdin;
    public          postgres    false    221   �k       X          0    28412 
   transports 
   TABLE DATA           d   COPY public.transports (id, transport_type, gov_number, car_brand, seats_count, driver) FROM stdin;
    public          postgres    false    223   3l       Z          0    28416    users 
   TABLE DATA           c   COPY public.users (id, username, firstname, surname, patronymic, passwd, status, role) FROM stdin;
    public          postgres    false    225   �l       k           0    0    drivers_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.drivers_id_seq', 12, true);
          public          postgres    false    210            l           0    0    end_points_id_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public.end_points_id_seq', 9, true);
          public          postgres    false    212            m           0    0    route_intervals_id_seq    SEQUENCE SET     D   SELECT pg_catalog.setval('public.route_intervals_id_seq', 5, true);
          public          postgres    false    214            n           0    0    routes_id_seq    SEQUENCE SET     ;   SELECT pg_catalog.setval('public.routes_id_seq', 6, true);
          public          postgres    false    216            o           0    0    schedule_id_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.schedule_id_seq', 17, true);
          public          postgres    false    218            p           0    0    tickets_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.tickets_id_seq', 23, true);
          public          postgres    false    220            q           0    0    transport_type_id_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public.transport_type_id_seq', 8, true);
          public          postgres    false    222            r           0    0    transports_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.transports_id_seq', 21, true);
          public          postgres    false    224            s           0    0    users_id_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.users_id_seq', 2, true);
          public          postgres    false    226            �           2606    28435    drivers drivers_pk 
   CONSTRAINT     P   ALTER TABLE ONLY public.drivers
    ADD CONSTRAINT drivers_pk PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.drivers DROP CONSTRAINT drivers_pk;
       public            postgres    false    209            �           2606    28437    end_points end_points_pk 
   CONSTRAINT     V   ALTER TABLE ONLY public.end_points
    ADD CONSTRAINT end_points_pk PRIMARY KEY (id);
 B   ALTER TABLE ONLY public.end_points DROP CONSTRAINT end_points_pk;
       public            postgres    false    211            �           2606    28439 "   route_intervals route_intervals_pk 
   CONSTRAINT     `   ALTER TABLE ONLY public.route_intervals
    ADD CONSTRAINT route_intervals_pk PRIMARY KEY (id);
 L   ALTER TABLE ONLY public.route_intervals DROP CONSTRAINT route_intervals_pk;
       public            postgres    false    213            �           2606    28441    routes routes_pk 
   CONSTRAINT     N   ALTER TABLE ONLY public.routes
    ADD CONSTRAINT routes_pk PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.routes DROP CONSTRAINT routes_pk;
       public            postgres    false    215            �           2606    28443    schedule schedule_pk 
   CONSTRAINT     R   ALTER TABLE ONLY public.schedule
    ADD CONSTRAINT schedule_pk PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.schedule DROP CONSTRAINT schedule_pk;
       public            postgres    false    217            �           2606    28445    tickets tickets_pk 
   CONSTRAINT     P   ALTER TABLE ONLY public.tickets
    ADD CONSTRAINT tickets_pk PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.tickets DROP CONSTRAINT tickets_pk;
       public            postgres    false    219            �           2606    28447 !   transport_types transport_type_pk 
   CONSTRAINT     _   ALTER TABLE ONLY public.transport_types
    ADD CONSTRAINT transport_type_pk PRIMARY KEY (id);
 K   ALTER TABLE ONLY public.transport_types DROP CONSTRAINT transport_type_pk;
       public            postgres    false    221            �           2606    28449    transports transports_pk 
   CONSTRAINT     V   ALTER TABLE ONLY public.transports
    ADD CONSTRAINT transports_pk PRIMARY KEY (id);
 B   ALTER TABLE ONLY public.transports DROP CONSTRAINT transports_pk;
       public            postgres    false    223            �           2606    28451    users users_pk 
   CONSTRAINT     L   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pk PRIMARY KEY (id);
 8   ALTER TABLE ONLY public.users DROP CONSTRAINT users_pk;
       public            postgres    false    225            �           1259    28452    drivers_id_uindex    INDEX     J   CREATE UNIQUE INDEX drivers_id_uindex ON public.drivers USING btree (id);
 %   DROP INDEX public.drivers_id_uindex;
       public            postgres    false    209            �           1259    28453    end_points_id_uindex    INDEX     P   CREATE UNIQUE INDEX end_points_id_uindex ON public.end_points USING btree (id);
 (   DROP INDEX public.end_points_id_uindex;
       public            postgres    false    211            �           1259    28454    route_intervals_id_uindex    INDEX     Z   CREATE UNIQUE INDEX route_intervals_id_uindex ON public.route_intervals USING btree (id);
 -   DROP INDEX public.route_intervals_id_uindex;
       public            postgres    false    213            �           1259    28455    routes_id_uindex    INDEX     H   CREATE UNIQUE INDEX routes_id_uindex ON public.routes USING btree (id);
 $   DROP INDEX public.routes_id_uindex;
       public            postgres    false    215            �           1259    28456    schedule_id_uindex    INDEX     L   CREATE UNIQUE INDEX schedule_id_uindex ON public.schedule USING btree (id);
 &   DROP INDEX public.schedule_id_uindex;
       public            postgres    false    217            �           1259    28457    tickets_id_uindex    INDEX     J   CREATE UNIQUE INDEX tickets_id_uindex ON public.tickets USING btree (id);
 %   DROP INDEX public.tickets_id_uindex;
       public            postgres    false    219            �           1259    28458    transport_type_id_uindex    INDEX     Y   CREATE UNIQUE INDEX transport_type_id_uindex ON public.transport_types USING btree (id);
 ,   DROP INDEX public.transport_type_id_uindex;
       public            postgres    false    221            �           1259    28459    transports_id_uindex    INDEX     P   CREATE UNIQUE INDEX transports_id_uindex ON public.transports USING btree (id);
 (   DROP INDEX public.transports_id_uindex;
       public            postgres    false    223            �           1259    28460    users_id_uindex    INDEX     F   CREATE UNIQUE INDEX users_id_uindex ON public.users USING btree (id);
 #   DROP INDEX public.users_id_uindex;
       public            postgres    false    225            �           1259    28461    users_username_uindex    INDEX     R   CREATE UNIQUE INDEX users_username_uindex ON public.users USING btree (username);
 )   DROP INDEX public.users_username_uindex;
       public            postgres    false    225            �           2620    28462 #   transports check_gov_number_trigger    TRIGGER     �   CREATE TRIGGER check_gov_number_trigger BEFORE INSERT OR UPDATE ON public.transports FOR EACH ROW EXECUTE FUNCTION public.check_gov_number();
 <   DROP TRIGGER check_gov_number_trigger ON public.transports;
       public          postgres    false    223    227            �           2620    28463 #   tickets check_tickets_count_trigger    TRIGGER     �   CREATE TRIGGER check_tickets_count_trigger BEFORE INSERT ON public.tickets FOR EACH ROW EXECUTE FUNCTION public.check_tickets_count();
 <   DROP TRIGGER check_tickets_count_trigger ON public.tickets;
       public          postgres    false    219    228            �           2606    28464    routes routes_end_points_id_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public.routes
    ADD CONSTRAINT routes_end_points_id_fk FOREIGN KEY (route_start_point) REFERENCES public.end_points(id);
 H   ALTER TABLE ONLY public.routes DROP CONSTRAINT routes_end_points_id_fk;
       public          postgres    false    211    215    3230            �           2606    28469     routes routes_end_points_id_fk_2    FK CONSTRAINT     �   ALTER TABLE ONLY public.routes
    ADD CONSTRAINT routes_end_points_id_fk_2 FOREIGN KEY (route_finish_point) REFERENCES public.end_points(id);
 J   ALTER TABLE ONLY public.routes DROP CONSTRAINT routes_end_points_id_fk_2;
       public          postgres    false    3230    211    215            �           2606    28474 #   routes routes_route_intervals_id_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public.routes
    ADD CONSTRAINT routes_route_intervals_id_fk FOREIGN KEY (route_interval) REFERENCES public.route_intervals(id);
 M   ALTER TABLE ONLY public.routes DROP CONSTRAINT routes_route_intervals_id_fk;
       public          postgres    false    215    3233    213            �           2606    28479    schedule schedule_routes_id_fk    FK CONSTRAINT     |   ALTER TABLE ONLY public.schedule
    ADD CONSTRAINT schedule_routes_id_fk FOREIGN KEY (route) REFERENCES public.routes(id);
 H   ALTER TABLE ONLY public.schedule DROP CONSTRAINT schedule_routes_id_fk;
       public          postgres    false    217    215    3236            �           2606    28484 "   schedule schedule_transports_id_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public.schedule
    ADD CONSTRAINT schedule_transports_id_fk FOREIGN KEY (transport) REFERENCES public.transports(id);
 L   ALTER TABLE ONLY public.schedule DROP CONSTRAINT schedule_transports_id_fk;
       public          postgres    false    217    223    3248            �           2606    28489    tickets tickets_schedule_id_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public.tickets
    ADD CONSTRAINT tickets_schedule_id_fk FOREIGN KEY (schedule) REFERENCES public.schedule(id);
 H   ALTER TABLE ONLY public.tickets DROP CONSTRAINT tickets_schedule_id_fk;
       public          postgres    false    219    217    3239            �           2606    28494 #   transports transports_drivers_id_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public.transports
    ADD CONSTRAINT transports_drivers_id_fk FOREIGN KEY (driver) REFERENCES public.drivers(id);
 M   ALTER TABLE ONLY public.transports DROP CONSTRAINT transports_drivers_id_fk;
       public          postgres    false    3227    223    209            �           2606    28499 +   transports transports_transport_types_id_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public.transports
    ADD CONSTRAINT transports_transport_types_id_fk FOREIGN KEY (transport_type) REFERENCES public.transport_types(id);
 U   ALTER TABLE ONLY public.transports DROP CONSTRAINT transports_transport_types_id_fk;
       public          postgres    false    221    223    3245            J   �   x���9�0Dk�0H	�]8L�

@ ��
V����o��d�E�p��o�/c�VV��C�p��%J�|��W��Ȑâ��)]:�z��s��0��}Q�FGId��^�
f�p�~Ϟ�����e�qZW��4����l�4��g0���]��(_���A�(vzM�`���V��Z���w��      L   �   x�m�;
�@��S,9@�����$
Z(jl����Dq5��
����$bc1��?�a:
x�,�W<돡��N�R'PO���~�[�+DH��A�Q�>�-�h�?�=,����MN4D�+d�ۀ�F
q�PH4��x9Y�ѣ���+P��L�ka��y.��HhҜpv�/AS��>��i      N   ^   x�3漰��>�{/l��~q�Ŧ{/v_��e�ya�m@�$ua���eQ|��/��~aP�)ԈM�/�^��6����=... ƓF�      P   Q   x�̻�PC��f���]�X���H*�ǒg�`��v���xkm]�l K	�5����� SI�]�N1�?I��XD�J��      R   �   x�u�K� е�K*��&p����ڊ�J��4�!��쏄�%o��0q�n(u6�nƽ�9��[%%���ζ_=/���i��P!�P�"k`�9�z�Qc�v��1f�")+DB\��%�dT��̔Hr�'z�r�Z'��\}H�s�RJo�Fx      T   1   x�3�4���4�2�4�0-9�b�!�ier�qZBX1z\\\ �p+      V   Q   x�3估��.6\�waÅM���Ƌ���8�L9/6���­vB�� ��m�;��9/��U삪������� �D�      X   �   x�}�M
�@���]*�8�q��al�ѥ�--��/72ٺp��}//����Jԃ�Qj�t�	Sm��ɷ���Bᇿ�'���(�(�R�D��YOjBM�3e�%��pÈ��LɋB�M])�¾�|f(�7��fn^5      Z   �   x�M�;�0  й=3�"XG�()�R�q)�[T"`��렉� �����������B��M���~	=R?�S��,�BY��8���;<����R��(@��o��5l=)r��UA/�X+��4��NV���\]�M����_�x���	!|�A1�     