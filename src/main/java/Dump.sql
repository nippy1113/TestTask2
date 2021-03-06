PGDMP                         y            test database    13.2    13.2     ?           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            ?           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            ?           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            ?           1262    16395    test database    DATABASE     l   CREATE DATABASE "test database" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'Russian_Russia.1251';
    DROP DATABASE "test database";
                postgres    false            ?            1259    16420 	   customers    TABLE     ]   CREATE TABLE public.customers (
    id integer NOT NULL,
    name text,
    lastname text
);
    DROP TABLE public.customers;
       public         heap    postgres    false            ?            1259    16428    products    TABLE     c   CREATE TABLE public.products (
    id integer NOT NULL,
    productname text,
    price integer
);
    DROP TABLE public.products;
       public         heap    postgres    false            ?            1259    16436 	   purchases    TABLE     y   CREATE TABLE public.purchases (
    id integer NOT NULL,
    productid integer,
    customerid integer,
    date date
);
    DROP TABLE public.purchases;
       public         heap    postgres    false            ?          0    16420 	   customers 
   TABLE DATA           7   COPY public.customers (id, name, lastname) FROM stdin;
    public          postgres    false    200   ^       ?          0    16428    products 
   TABLE DATA           :   COPY public.products (id, productname, price) FROM stdin;
    public          postgres    false    201   ?       ?          0    16436 	   purchases 
   TABLE DATA           D   COPY public.purchases (id, productid, customerid, date) FROM stdin;
    public          postgres    false    202   ?       +           2606    16427    customers customers_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY public.customers
    ADD CONSTRAINT customers_pkey PRIMARY KEY (id);
 B   ALTER TABLE ONLY public.customers DROP CONSTRAINT customers_pkey;
       public            postgres    false    200            -           2606    16435    products products_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.products
    ADD CONSTRAINT products_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.products DROP CONSTRAINT products_pkey;
       public            postgres    false    201            /           2606    16440    purchases purchases_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY public.purchases
    ADD CONSTRAINT purchases_pkey PRIMARY KEY (id);
 B   ALTER TABLE ONLY public.purchases DROP CONSTRAINT purchases_pkey;
       public            postgres    false    202            ?   =   x?3??,K??t/ʯ????2?t?I?H?KI-???M??/?2?t?/?,?t?O)?c???? ??Z      ?   +   x?3?L??N?4?2??*?LN?44?2?LJM-?42?????? ???      ?   e   x?U???0ߨ?d,??%??????b?4MI?I|?!˫6(?>???x{@G?'??$??8)?!V?t9T?r?@w\;T`??I	??.k?p?{???)b     