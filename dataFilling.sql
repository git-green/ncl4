DELETE FROM PRODUCT_CATEGORY;
DELETE FROM CATEGORIES;
DELETE FROM PICTURES;
DELETE FROM PRODUCTS;
DELETE FROM USERS;



DROP SEQUENCE USER_ID_S;
DROP SEQUENCE PRODUCT_ID_S;
DROP SEQUENCE FOLLOWING_ID_S;
DROP SEQUENCE CATEGORY_ID_S;

CREATE SEQUENCE USER_ID_S START WITH 1 INCREMENT BY 1 NOMAXVALUE;
CREATE SEQUENCE PRODUCT_ID_S START WITH 1 INCREMENT BY 1 NOMAXVALUE;
CREATE SEQUENCE FOLLOWING_ID_S START WITH 1 INCREMENT BY 1 NOMAXVALUE;
CREATE SEQUENCE CATEGORY_ID_S START WITH 1 INCREMENT BY 1 NOMAXVALUE;

INSERT INTO USERS(
    ID,
    LOGIN,
    PASSWORD,
    NAME,
    SECOND_NAME,
    BIRTH,
    EMAIL,
    PHONE,
    REGISTRATION_DATE,
    STATUS
)VALUES(
    USER_ID_S.NEXTVAL,
    'admin',
    '73acd9a5972130b75066c82595a1fae3',
    'admin',
    'admin',
    TO_DATE('1999/11/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss'),
    'admin@gmail.com',
    '00000000',
    SYSDATE,
    'admin'
);

INSERT INTO USERS(
    ID,
    LOGIN,
    PASSWORD,
    NAME,
    SECOND_NAME,
    BIRTH,
    EMAIL,
    PHONE,
    REGISTRATION_DATE,
    STATUS
)VALUES(
    USER_ID_S.NEXTVAL,
    'green',
    '9f27410725ab8cc8854a2769c7a516b8',--green
    'Dima',
    'Green',
    TO_DATE('1990/04/01 21:02:44', 'yyyy/mm/dd hh24:mi:ss'),
    'green@gmail.com',
    '123456789',
    SYSDATE,
    'admin'
);

INSERT INTO USERS(
    ID,
    LOGIN,
    PASSWORD,
    NAME,
    SECOND_NAME,
    BIRTH,
    EMAIL,
    PHONE,
    REGISTRATION_DATE,
    STATUS
)VALUES(
    USER_ID_S.NEXTVAL,
    '123',
    '202cb962ac59075b964b07152d234b70',--123
    'alex',
    'smith',
    TO_DATE('1985/08/02 21:02:44', 'yyyy/mm/dd hh24:mi:ss'),
    'alex.alex@gmail.com',
    '987654321',
    SYSDATE,
    'user'
);

INSERT INTO CATEGORIES(
    ID,--1
    NAME
)VALUES(
    CATEGORY_ID_S.NEXTVAL,
    'Mobiles'
);

INSERT INTO CATEGORIES(
    ID,--2
    NAME
)VALUES(
    CATEGORY_ID_S.NEXTVAL,
    'Appliances'
);

INSERT INTO CATEGORIES(
    ID,--3
    NAME
)VALUES(
    CATEGORY_ID_S.NEXTVAL,
    'Computers'
);

INSERT INTO CATEGORIES(
    ID,--4
    PARENT_ID,
    NAME
)VALUES(
    CATEGORY_ID_S.NEXTVAL,
    1,
    'Android'
);

INSERT INTO CATEGORIES(
    ID,--5
    PARENT_ID,
    NAME
)VALUES(
    CATEGORY_ID_S.NEXTVAL,
    1,
    'IOS'
);

INSERT INTO CATEGORIES(
    ID,--6
    PARENT_ID,
    NAME
)VALUES(
    CATEGORY_ID_S.NEXTVAL,
    1,
    'Microsoft'
);

INSERT INTO CATEGORIES(
    ID,--7
    PARENT_ID,
    NAME
)VALUES(
    CATEGORY_ID_S.NEXTVAL,
    2,
    'Large appliances'
);

INSERT INTO CATEGORIES(
    ID,--8
    PARENT_ID,
    NAME
)VALUES(
    CATEGORY_ID_S.NEXTVAL,
    2,
    'Small appliances'
);

INSERT INTO CATEGORIES(
    ID,--9
    PARENT_ID,
    NAME
)VALUES(
    CATEGORY_ID_S.NEXTVAL,
    7,
    'Refrigerators'
);

INSERT INTO CATEGORIES(
    ID,--10
    PARENT_ID,
    NAME
)VALUES(
    CATEGORY_ID_S.NEXTVAL,
    7,
    'Microwaves'
);

INSERT INTO CATEGORIES(
    ID,--11
    PARENT_ID,
    NAME
)VALUES(
    CATEGORY_ID_S.NEXTVAL,
    7,
    'Food processors'
);

INSERT INTO CATEGORIES(
    ID,--12
    PARENT_ID,
    NAME
)VALUES(
    CATEGORY_ID_S.NEXTVAL,
    7,
    'Washers'
);

INSERT INTO CATEGORIES(
    ID,--13
    PARENT_ID,
    NAME
)VALUES(
    CATEGORY_ID_S.NEXTVAL,
    7,
    'Vacuum cleaners'
);

INSERT INTO CATEGORIES(
    ID,--14
    PARENT_ID,
    NAME
)VALUES(
    CATEGORY_ID_S.NEXTVAL,
    8,
    'Scales'
);

INSERT INTO CATEGORIES(
    ID,--15
    PARENT_ID,
    NAME
)VALUES(
    CATEGORY_ID_S.NEXTVAL,
    8,
    'Toasters'
);

INSERT INTO CATEGORIES(
    ID,--16
    PARENT_ID,
    NAME
)VALUES(
    CATEGORY_ID_S.NEXTVAL,
    8,
    'Mixers'
);

INSERT INTO CATEGORIES(
    ID,--17
    PARENT_ID,
    NAME
)VALUES(
    CATEGORY_ID_S.NEXTVAL,
    8,
    'Blenders'
);

INSERT INTO CATEGORIES(
    ID,--18
    PARENT_ID,
    NAME
)VALUES(
    CATEGORY_ID_S.NEXTVAL,
    8,
    'Coffee Makers'
);

INSERT INTO CATEGORIES(
    ID,--19
    PARENT_ID,
    NAME
)VALUES(
    CATEGORY_ID_S.NEXTVAL,
    8,
    'Hair Dryers'
);

INSERT INTO CATEGORIES(
    ID,--20
    PARENT_ID,
    NAME
)VALUES(
    CATEGORY_ID_S.NEXTVAL,
    8,
    'Shavers'
);

INSERT INTO CATEGORIES(
    ID,--21
    PARENT_ID,
    NAME
)VALUES(
    CATEGORY_ID_S.NEXTVAL,
    8,
    'Fans'
);


INSERT INTO PRODUCTS(
    ID,
    SELLER_ID,
    NAME,
    DESCRIPTION,
    START_DATE,
    END_DATE,
    START_PRICE,
    BUYOUT_PRICE,
    CURRENT_PRICE,
    CURRENT_BUYER_ID,
    IS_ACTIVE
) VALUES (
    PRODUCT_ID_S.NEXTVAL,
    2,
    'IPHONE 6 128GB SILVER',
    'f/2.2, Focus Pixels, �������� �� 6 ����, IR-������, ������ BSI, ������ ������� ���������� �������',
    TO_DATE('2015/12/05 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    TO_DATE('2015/12/15 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    20000,
    30764,
    0,
    NULL,
    'active'
);

INSERT INTO PICTURES (
    PRODUCT_ID,
    URL
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    'images/product-images/IPHONE6128GBSILVER.jpeg'
);
    
INSERT INTO PRODUCT_CATEGORY (
    PRODUCT_ID,
    CATEGORY_ID
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    5
);

INSERT INTO PRODUCTS(
    ID,
    SELLER_ID,
    NAME,
    DESCRIPTION,
    START_DATE,
    END_DATE,
    START_PRICE,
    BUYOUT_PRICE,
    CURRENT_PRICE,
    CURRENT_BUYER_ID,
    IS_ACTIVE
) VALUES (
    PRODUCT_ID_S.NEXTVAL,
    2,
    'APPLE IPHONE 6 GREY',
    'Retina HD display, ������������� 1400:1, ������������ ������� 500 ��/�2, �������� ����� Full sRGB, ���������� ��������',
    TO_DATE('2015/12/05 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    TO_DATE('2015/12/15 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    23000,
    29300,
    0,
    NULL,
    'active'
);

INSERT INTO PICTURES (
    PRODUCT_ID,
    URL
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    'images/product-images/APPLEIPHONE6GREY.jpeg'
);
    
INSERT INTO PRODUCT_CATEGORY (
    PRODUCT_ID,
    CATEGORY_ID
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    5
);


INSERT INTO PRODUCTS(
    ID,
    SELLER_ID,
    NAME,
    DESCRIPTION,
    START_DATE,
    END_DATE,
    START_PRICE,
    BUYOUT_PRICE,
    CURRENT_PRICE,
    CURRENT_BUYER_ID,
    IS_ACTIVE
) VALUES (
    PRODUCT_ID_S.NEXTVAL,
    2,
    'APPLE IPHONE 6 64GB SILVER',
    'Retina HD display, ������������� 1400:1, ������������ ������� 500 ��/�2, �������� ����� Full sRGB, ���������� ��������',
    TO_DATE('2015/12/05 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    TO_DATE('2015/12/15 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    19000,
    27194,
    0,
    NULL,
    'active'
);

INSERT INTO PICTURES (
    PRODUCT_ID,
    URL
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    'images/product-images/APPLEIPHONE664GBSILVER.jpg'
);
    
INSERT INTO PRODUCT_CATEGORY (
    PRODUCT_ID,
    CATEGORY_ID
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    5
);



INSERT INTO PRODUCTS(
    ID,
    SELLER_ID,
    NAME,
    DESCRIPTION,
    START_DATE,
    END_DATE,
    START_PRICE,
    BUYOUT_PRICE,
    CURRENT_PRICE,
    CURRENT_BUYER_ID,
    IS_ACTIVE
) VALUES (
    PRODUCT_ID_S.NEXTVAL,
    2,
    'IPHONE 6 PLUS 16GB GOLD',
    '������ �� 24� � ������ ��������� (3G), �� 384� � ������ ��������, �� 12� 3G/LTE/Wi-Fi ���������, �� 14� ������������ �����, �� 80� ��������������� ������',
    TO_DATE('2015/12/05 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    TO_DATE('2015/12/15 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    17000,
    25899,
    0,
    NULL,
    'active'
);

INSERT INTO PICTURES (
    PRODUCT_ID,
    URL
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    'images/product-images/IPHONE6PLUS16GBGOLD.jpeg'
);
    
INSERT INTO PRODUCT_CATEGORY (
    PRODUCT_ID,
    CATEGORY_ID
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    5
);


INSERT INTO PRODUCTS(
    ID,
    SELLER_ID,
    NAME,
    DESCRIPTION,
    START_DATE,
    END_DATE,
    START_PRICE,
    BUYOUT_PRICE,
    CURRENT_PRICE,
    CURRENT_BUYER_ID,
    IS_ACTIVE
) VALUES (
    PRODUCT_ID_S.NEXTVAL,
    2,
    'IPHONE 6 GOLD',
    'f/2.2, Focus Pixels, �������� �� 6 ����, IR-������, ������ BSI, ������ ������� ���������� �������',
    TO_DATE('2015/12/05 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    TO_DATE('2015/12/15 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    17000,
    25646,
    0,
    NULL,
    'active'
);

INSERT INTO PICTURES (
    PRODUCT_ID,
    URL
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    'images/product-images/IPHONE6GOLD.jpeg'
);
    
INSERT INTO PRODUCT_CATEGORY (
    PRODUCT_ID,
    CATEGORY_ID
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    5
);


INSERT INTO PRODUCTS(
    ID,
    SELLER_ID,
    NAME,
    DESCRIPTION,
    START_DATE,
    END_DATE,
    START_PRICE,
    BUYOUT_PRICE,
    CURRENT_PRICE,
    CURRENT_BUYER_ID,
    IS_ACTIVE
) VALUES (
    PRODUCT_ID_S.NEXTVAL,
    2,
    'IPHONE 6 16GB GOLD',
    '������ ���������� ������� (Touch ID), �������� �������, ��������, ��������, ������������, ������ �����������',
    TO_DATE('2015/12/05 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    TO_DATE('2015/12/15 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    15000,
    22574,
    0,
    NULL,
    'active'
);

INSERT INTO PICTURES (
    PRODUCT_ID,
    URL
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    'images/product-images/IPHONE616GBGOLD.jpeg'
);
    
INSERT INTO PRODUCT_CATEGORY (
    PRODUCT_ID,
    CATEGORY_ID
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    5
);


INSERT INTO PRODUCTS(
    ID,
    SELLER_ID,
    NAME,
    DESCRIPTION,
    START_DATE,
    END_DATE,
    START_PRICE,
    BUYOUT_PRICE,
    CURRENT_PRICE,
    CURRENT_BUYER_ID,
    IS_ACTIVE
) VALUES (
    PRODUCT_ID_S.NEXTVAL,
    2,
    'IPHONE 6 16GB SILVER',
    '������ ���������� ������� (Touch ID), �������� �������, ��������, ��������, ������������, ������ �����������',
    TO_DATE('2015/12/05 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    TO_DATE('2015/12/15 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    15000,
    22574,
    0,
    NULL,
    'active'
);

INSERT INTO PICTURES (
    PRODUCT_ID,
    URL
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    'images/product-images/IPHONE616GBSILVER.jpeg'
);
    
INSERT INTO PRODUCT_CATEGORY (
    PRODUCT_ID,
    CATEGORY_ID
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    5
);


INSERT INTO PRODUCTS(
    ID,
    SELLER_ID,
    NAME,
    DESCRIPTION,
    START_DATE,
    END_DATE,
    START_PRICE,
    BUYOUT_PRICE,
    CURRENT_PRICE,
    CURRENT_BUYER_ID,
    IS_ACTIVE
) VALUES (
    PRODUCT_ID_S.NEXTVAL,
    2,
    'IPHONE 6 16GB GREY',
    'Apple A8 + GPU PowerVR GX6450',
    TO_DATE('2015/12/05 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    TO_DATE('2015/12/15 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    12000,
    19999,
    0,
    NULL,
    'active'
);

INSERT INTO PICTURES (
    PRODUCT_ID,
    URL
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    'images/product-images/IPHONE616GBGREY.jpeg'
);
    
INSERT INTO PRODUCT_CATEGORY (
    PRODUCT_ID,
    CATEGORY_ID
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    5
);


INSERT INTO PRODUCTS(
    ID,
    SELLER_ID,
    NAME,
    DESCRIPTION,
    START_DATE,
    END_DATE,
    START_PRICE,
    BUYOUT_PRICE,
    CURRENT_PRICE,
    CURRENT_BUYER_ID,
    IS_ACTIVE
) VALUES (
    PRODUCT_ID_S.NEXTVAL,
    3,
    'BLACKBERRY Q10 BLACK',
    'image stabilization, ����������� ������ ���������� ����� 1280�720 ����� 30 �/�',
    TO_DATE('2015/12/05 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    TO_DATE('2015/12/15 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    5000,
    7777,
    0,
    NULL,
    'active'
);

INSERT INTO PICTURES (
    PRODUCT_ID,
    URL
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    'images/product-images/BLACKBERRYQ10BLACK.jpeg'
);
    
INSERT INTO PRODUCT_CATEGORY (
    PRODUCT_ID,
    CATEGORY_ID
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    4
);


INSERT INTO PRODUCTS(
    ID,
    SELLER_ID,
    NAME,
    DESCRIPTION,
    START_DATE,
    END_DATE,
    START_PRICE,
    BUYOUT_PRICE,
    CURRENT_PRICE,
    CURRENT_BUYER_ID,
    IS_ACTIVE
) VALUES (
    PRODUCT_ID_S.NEXTVAL,
    3,
    'BLACKBERRY Z3 BLACK',
    '����� ������ �� 15,5� � ������ ���������, �� 16,2� � ������ ��������',
    TO_DATE('2015/12/05 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    TO_DATE('2015/12/15 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    4500,
    5062,
    0,
    NULL,
    'active'
);

INSERT INTO PICTURES (
    PRODUCT_ID,
    URL
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    'images/product-images/BLACKBERRYZ3BLACK.jpeg'
);
    
INSERT INTO PRODUCT_CATEGORY (
    PRODUCT_ID,
    CATEGORY_ID
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    4
);


INSERT INTO PRODUCTS(
    ID,
    SELLER_ID,
    NAME,
    DESCRIPTION,
    START_DATE,
    END_DATE,
    START_PRICE,
    BUYOUT_PRICE,
    CURRENT_PRICE,
    CURRENT_BUYER_ID,
    IS_ACTIVE
) VALUES (
    PRODUCT_ID_S.NEXTVAL,
    3,
    'FLY IQ4416 BLACK',
    '16 ���. ������, �������� ������, OGS',
    TO_DATE('2015/12/05 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    TO_DATE('2015/12/15 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    1500,
    1875,
    0,
    NULL,
    'active'
);

INSERT INTO PICTURES (
    PRODUCT_ID,
    URL
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    'images/product-images/FLYIQ4416BLACK.jpeg'
);
    
INSERT INTO PRODUCT_CATEGORY (
    PRODUCT_ID,
    CATEGORY_ID
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    4
);


INSERT INTO PRODUCTS(
    ID,
    SELLER_ID,
    NAME,
    DESCRIPTION,
    START_DATE,
    END_DATE,
    START_PRICE,
    BUYOUT_PRICE,
    CURRENT_PRICE,
    CURRENT_BUYER_ID,
    IS_ACTIVE
) VALUES (
    PRODUCT_ID_S.NEXTVAL,
    3,
    'FLY IQ456 DUAL SIM BLACK',
    '������� ����������� � ���������, ������������, �������� �-GPS, �����������',
    TO_DATE('2015/12/05 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    TO_DATE('2015/12/15 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    2000,
    2226,
    0,
    NULL,
    'active'
);

INSERT INTO PICTURES (
    PRODUCT_ID,
    URL
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    'images/product-images/FLYIQ456DUALSIMBLACK.jpeg'
);
    
INSERT INTO PRODUCT_CATEGORY (
    PRODUCT_ID,
    CATEGORY_ID
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    4
);


INSERT INTO PRODUCTS(
    ID,
    SELLER_ID,
    NAME,
    DESCRIPTION,
    START_DATE,
    END_DATE,
    START_PRICE,
    BUYOUT_PRICE,
    CURRENT_PRICE,
    CURRENT_BUYER_ID,
    IS_ACTIVE
) VALUES (
    PRODUCT_ID_S.NEXTVAL,
    3,
    'FLY IQ4516 TORNADO SLIM BLACK',
    'MediaTek MT6592� + GPU Mali-450MP',
    TO_DATE('2015/12/05 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    TO_DATE('2015/12/15 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    3000,
    3966,
    0,
    NULL,
    'active'
);

INSERT INTO PICTURES (
    PRODUCT_ID,
    URL
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    'images/product-images/FLYIQ4516TORNADOSLIMBLACK.jpeg'
);
    
INSERT INTO PRODUCT_CATEGORY (
    PRODUCT_ID,
    CATEGORY_ID
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    4
);



INSERT INTO PRODUCTS(
    ID,
    SELLER_ID,
    NAME,
    DESCRIPTION,
    START_DATE,
    END_DATE,
    START_PRICE,
    BUYOUT_PRICE,
    CURRENT_PRICE,
    CURRENT_BUYER_ID,
    IS_ACTIVE
) VALUES (
    PRODUCT_ID_S.NEXTVAL,
    3,
    'LG D380 L80 DUAL SIM WHITE',
    'Qualcomm Snapdragon 200 CPU + GPU Adreno 302',
    TO_DATE('2015/12/05 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    TO_DATE('2015/12/15 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    3300,
    3562,
    0,
    NULL,
    'active'
);

INSERT INTO PICTURES (
    PRODUCT_ID,
    URL
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    'images/product-images/LGD380L80DUALSIMWHITE.jpeg'
);
    
INSERT INTO PRODUCT_CATEGORY (
    PRODUCT_ID,
    CATEGORY_ID
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    4
);


INSERT INTO PRODUCTS(
    ID,
    SELLER_ID,
    NAME,
    DESCRIPTION,
    START_DATE,
    END_DATE,
    START_PRICE,
    BUYOUT_PRICE,
    CURRENT_PRICE,
    CURRENT_BUYER_ID,
    IS_ACTIVE
) VALUES (
    PRODUCT_ID_S.NEXTVAL,
    3,
    'LG D335 DUAL SIM WHITE',
    'MediaTek MT6582 + GPU Mali-400MP2',
    TO_DATE('2015/12/05 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    TO_DATE('2015/12/15 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    3700,
    4000,
    0,
    NULL,
    'active'
);

INSERT INTO PICTURES (
    PRODUCT_ID,
    URL
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    'images/product-images/LGD335DUALSIMWHITE.jpeg'
);
    
INSERT INTO PRODUCT_CATEGORY (
    PRODUCT_ID,
    CATEGORY_ID
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    4
);


INSERT INTO PRODUCTS(
    ID,
    SELLER_ID,
    NAME,
    DESCRIPTION,
    START_DATE,
    END_DATE,
    START_PRICE,
    BUYOUT_PRICE,
    CURRENT_PRICE,
    CURRENT_BUYER_ID,
    IS_ACTIVE
) VALUES (
    PRODUCT_ID_S.NEXTVAL,
    3,
    'SONY XPERIA Z3',
    '������� Exmor RS, 8� �������� ���, ������ ����� � ������� 4K, ����� ���������, HDR - ����� ���������, ������������ �����������, ������������� ���, ���-�����, ����� �������� ������',
    TO_DATE('2015/12/05 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    TO_DATE('2015/12/15 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    17900,
    18900,
    0,
    NULL,
    'active'
);

INSERT INTO PICTURES (
    PRODUCT_ID,
    URL
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    'images/product-images/SONYXPERIAZ3.jpeg'
);
    
INSERT INTO PRODUCT_CATEGORY (
    PRODUCT_ID,
    CATEGORY_ID
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    4
);



INSERT INTO PRODUCTS(
    ID,
    SELLER_ID,
    NAME,
    DESCRIPTION,
    START_DATE,
    END_DATE,
    START_PRICE,
    BUYOUT_PRICE,
    CURRENT_PRICE,
    CURRENT_BUYER_ID,
    IS_ACTIVE
) VALUES (
    PRODUCT_ID_S.NEXTVAL,
    3,
    'SONY XPERIA Z2 D6502 PURPLE',
    '������ Exmor RS, �������� ������������ �����������, ��������� ������� ������',
    TO_DATE('2015/12/05 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    TO_DATE('2015/12/15 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    9000,
    9972,
    0,
    NULL,
    'active'
);

INSERT INTO PICTURES (
    PRODUCT_ID,
    URL
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    'images/product-images/SONYXPERIAZ2D6502PURPLE.jpeg'
);
    
INSERT INTO PRODUCT_CATEGORY (
    PRODUCT_ID,
    CATEGORY_ID
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    4
);


INSERT INTO PRODUCTS(
    ID,
    SELLER_ID,
    NAME,
    DESCRIPTION,
    START_DATE,
    END_DATE,
    START_PRICE,
    BUYOUT_PRICE,
    CURRENT_PRICE,
    CURRENT_BUYER_ID,
    IS_ACTIVE
) VALUES (
    PRODUCT_ID_S.NEXTVAL,
    3,
    'SONY XPERIA T2 ULTRA D5322 DUAL SIM SIM WHITE 1280-7249',
    '������� Sony Exmor RS, ����������, ������������ �����������, �������� ��� 16�, ����������� ����������� ������� 720�',
    TO_DATE('2015/12/05 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    TO_DATE('2015/12/15 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    5000,
    7047,
    0,
    NULL,
    'active'
);

INSERT INTO PICTURES (
    PRODUCT_ID,
    URL
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    'images/product-images/SONY.jpeg'
);
    
INSERT INTO PRODUCT_CATEGORY (
    PRODUCT_ID,
    CATEGORY_ID
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    4
);


INSERT INTO PRODUCTS(
    ID,
    SELLER_ID,
    NAME,
    DESCRIPTION,
    START_DATE,
    END_DATE,
    START_PRICE,
    BUYOUT_PRICE,
    CURRENT_PRICE,
    CURRENT_BUYER_ID,
    IS_ACTIVE
) VALUES (
    PRODUCT_ID_S.NEXTVAL,
    3,
    'LG G3 D690 TITAN',
    'MediaTek MT6582 + GPU Mali-400MP2',
    TO_DATE('2015/12/05 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    TO_DATE('2015/12/15 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    4000,
    4500,
    0,
    NULL,
    'active'
);

INSERT INTO PICTURES (
    PRODUCT_ID,
    URL
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    'images/product-images/LGG3D690TITAN.jpeg'
);
    
INSERT INTO PRODUCT_CATEGORY (
    PRODUCT_ID,
    CATEGORY_ID
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    4
);


INSERT INTO PRODUCTS(
    ID,
    SELLER_ID,
    NAME,
    DESCRIPTION,
    START_DATE,
    END_DATE,
    START_PRICE,
    BUYOUT_PRICE,
    CURRENT_PRICE,
    CURRENT_BUYER_ID,
    IS_ACTIVE
) VALUES (
    PRODUCT_ID_S.NEXTVAL,
    3,
    'SONY XPERIA Z3 COMPACT D5803 ORANGE',
    'MediaTek MT6582 + GPU Mali-400MP2',
    TO_DATE('2015/12/05 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    TO_DATE('2015/12/15 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    4000,
    4500,
    0,
    NULL,
    'active'
);

INSERT INTO PICTURES (
    PRODUCT_ID,
    URL
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    'images/product-images/SONYXPERIAZ3COMPACTD5803ORANGE.jpeg'
);
    
INSERT INTO PRODUCT_CATEGORY (
    PRODUCT_ID,
    CATEGORY_ID
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    4
);


INSERT INTO PRODUCTS(
    ID,
    SELLER_ID,
    NAME,
    DESCRIPTION,
    START_DATE,
    END_DATE,
    START_PRICE,
    BUYOUT_PRICE,
    CURRENT_PRICE,
    CURRENT_BUYER_ID,
    IS_ACTIVE
) VALUES (
    PRODUCT_ID_S.NEXTVAL,
    3,
    'LG LEON Y50',
    'MediaTek MT6582 + GPU Mali-400MP2',
    TO_DATE('2015/12/05 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    TO_DATE('2015/12/15 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    4000,
    4500,
    0,
    NULL,
    'active'
);

INSERT INTO PICTURES (
    PRODUCT_ID,
    URL
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    'images/product-images/LGLEONY50.jpeg'
);
    
INSERT INTO PRODUCT_CATEGORY (
    PRODUCT_ID,
    CATEGORY_ID
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    4
);


INSERT INTO PRODUCTS(
    ID,
    SELLER_ID,
    NAME,
    DESCRIPTION,
    START_DATE,
    END_DATE,
    START_PRICE,
    BUYOUT_PRICE,
    CURRENT_PRICE,
    CURRENT_BUYER_ID,
    IS_ACTIVE
) VALUES (
    PRODUCT_ID_S.NEXTVAL,
    3,
    'LG G3SD 724 TITAN',
    'MediaTek MT6582 + GPU Mali-400MP2',
    TO_DATE('2015/12/05 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    TO_DATE('2015/12/15 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    4200,
    4700,
    0,
    NULL,
    'active'
);

INSERT INTO PICTURES (
    PRODUCT_ID,
    URL
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    'images/product-images/LGG3SD724TITAN.jpeg'
);
    
INSERT INTO PRODUCT_CATEGORY (
    PRODUCT_ID,
    CATEGORY_ID
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    4
);



INSERT INTO PRODUCTS(
    ID,
    SELLER_ID,
    NAME,
    DESCRIPTION,
    START_DATE,
    END_DATE,
    START_PRICE,
    BUYOUT_PRICE,
    CURRENT_PRICE,
    CURRENT_BUYER_ID,
    IS_ACTIVE
) VALUES (
    PRODUCT_ID_S.NEXTVAL,
    3,
    'MICROSOFT NOKIA LUMIA 430',
    '������� ����������� � ���������, ������������, �������� �-GPS, �����������, ���������� �������� ��������� 30 ��',
    TO_DATE('2015/12/05 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    TO_DATE('2015/12/15 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    1500,
    1600,
    0,
    NULL,
    'active'
);

INSERT INTO PICTURES (
    PRODUCT_ID,
    URL
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    'images/product-images/MICROSOFTNOKIALUMIA430.jpeg'
);
    
INSERT INTO PRODUCT_CATEGORY (
    PRODUCT_ID,
    CATEGORY_ID
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    6
);


INSERT INTO PRODUCTS(
    ID,
    SELLER_ID,
    NAME,
    DESCRIPTION,
    START_DATE,
    END_DATE,
    START_PRICE,
    BUYOUT_PRICE,
    CURRENT_PRICE,
    CURRENT_BUYER_ID,
    IS_ACTIVE
) VALUES (
    PRODUCT_ID_S.NEXTVAL,
    3,
    'MICROSOFT LUMIA 535',
    'Qualcomm Snapdragon 200 (MSM8610) + GPU Adreno 302',
    TO_DATE('2015/12/05 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    TO_DATE('2015/12/15 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    2500,
    2928,
    0,
    NULL,
    'active'
);

INSERT INTO PICTURES (
    PRODUCT_ID,
    URL
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    'images/product-images/MICROSOFTLUMIA535.jpeg'
);
    
INSERT INTO PRODUCT_CATEGORY (
    PRODUCT_ID,
    CATEGORY_ID
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    6
);


INSERT INTO PRODUCTS(
    ID,
    SELLER_ID,
    NAME,
    DESCRIPTION,
    START_DATE,
    END_DATE,
    START_PRICE,
    BUYOUT_PRICE,
    CURRENT_PRICE,
    CURRENT_BUYER_ID,
    IS_ACTIVE
) VALUES (
    PRODUCT_ID_S.NEXTVAL,
    3,
    'MICROSOFT LUMIA 540 DUAL SIM BLACK',
    'Qualcomm Snapdragon 200 (MSM8610) + GPU Adreno 302',
    TO_DATE('2015/12/05 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    TO_DATE('2015/12/15 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    4000,
    4335,
    0,
    NULL,
    'active'
);

INSERT INTO PICTURES (
    PRODUCT_ID,
    URL
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    'images/product-images/MICROSOFTLUMIA540DUALSIMBLACK.jpeg'
);
    
INSERT INTO PRODUCT_CATEGORY (
    PRODUCT_ID,
    CATEGORY_ID
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    6
);


INSERT INTO PRODUCTS(
    ID,
    SELLER_ID,
    NAME,
    DESCRIPTION,
    START_DATE,
    END_DATE,
    START_PRICE,
    BUYOUT_PRICE,
    CURRENT_PRICE,
    CURRENT_BUYER_ID,
    IS_ACTIVE
) VALUES (
    PRODUCT_ID_S.NEXTVAL,
    3,
    '��������� BRAIN ENTERTAINMENT MINI B60',
    'BRAIN',
    TO_DATE('2015/12/05 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    TO_DATE('2015/12/15 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    5000,
    6000,
    0,
    NULL,
    'active'
);

INSERT INTO PICTURES (
    PRODUCT_ID,
    URL
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    'images/product-images/B60.jpeg'
);
    
INSERT INTO PRODUCT_CATEGORY (
    PRODUCT_ID,
    CATEGORY_ID
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    3
);

INSERT INTO PRODUCTS(
    ID,
    SELLER_ID,
    NAME,
    DESCRIPTION,
    START_DATE,
    END_DATE,
    START_PRICE,
    BUYOUT_PRICE,
    CURRENT_PRICE,
    CURRENT_BUYER_ID,
    IS_ACTIVE
) VALUES (
    PRODUCT_ID_S.NEXTVAL,
    3,
    '�������',
    '�������',
    TO_DATE('2015/12/05 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    TO_DATE('2015/12/15 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    1,
    10000,
    0,
    NULL,
    'active'
);

INSERT INTO PICTURES (
    PRODUCT_ID,
    URL
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    'images/product-images/blender.jpeg'
);
    
INSERT INTO PRODUCT_CATEGORY (
    PRODUCT_ID,
    CATEGORY_ID
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    17
);

INSERT INTO PRODUCTS(
    ID,
    SELLER_ID,
    NAME,
    DESCRIPTION,
    START_DATE,
    END_DATE,
    START_PRICE,
    BUYOUT_PRICE,
    CURRENT_PRICE,
    CURRENT_BUYER_ID,
    IS_ACTIVE
) VALUES (
    PRODUCT_ID_S.NEXTVAL,
    3,
    '���������',
    '���������',
    TO_DATE('2015/12/05 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    TO_DATE('2015/12/15 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    1,
    10000,
    0,
    NULL,
    'active'
);

INSERT INTO PICTURES (
    PRODUCT_ID,
    URL
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    'images/product-images/coffee.jpeg'
);
    
INSERT INTO PRODUCT_CATEGORY (
    PRODUCT_ID,
    CATEGORY_ID
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    18
);

INSERT INTO PRODUCTS(
    ID,
    SELLER_ID,
    NAME,
    DESCRIPTION,
    START_DATE,
    END_DATE,
    START_PRICE,
    BUYOUT_PRICE,
    CURRENT_PRICE,
    CURRENT_BUYER_ID,
    IS_ACTIVE
) VALUES (
    PRODUCT_ID_S.NEXTVAL,
    3,
    '�������� �������',
    '�������� �������',
    TO_DATE('2015/12/05 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    TO_DATE('2015/12/15 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    1,
    10000,
    0,
    NULL,
    'active'
);

INSERT INTO PICTURES (
    PRODUCT_ID,
    URL
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    'images/product-images/food.jpeg'
);
    
INSERT INTO PRODUCT_CATEGORY (
    PRODUCT_ID,
    CATEGORY_ID
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    11
);

INSERT INTO PRODUCTS(
    ID,
    SELLER_ID,
    NAME,
    DESCRIPTION,
    START_DATE,
    END_DATE,
    START_PRICE,
    BUYOUT_PRICE,
    CURRENT_PRICE,
    CURRENT_BUYER_ID,
    IS_ACTIVE
) VALUES (
    PRODUCT_ID_S.NEXTVAL,
    3,
    '�������������',
    '�������������',
    TO_DATE('2015/12/05 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    TO_DATE('2015/12/15 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    1,
    10000,
    0,
    NULL,
    'active'
);

INSERT INTO PICTURES (
    PRODUCT_ID,
    URL
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    'images/product-images/microwave.jpeg'
);
    
INSERT INTO PRODUCT_CATEGORY (
    PRODUCT_ID,
    CATEGORY_ID
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    10
);

INSERT INTO PRODUCTS(
    ID,
    SELLER_ID,
    NAME,
    DESCRIPTION,
    START_DATE,
    END_DATE,
    START_PRICE,
    BUYOUT_PRICE,
    CURRENT_PRICE,
    CURRENT_BUYER_ID,
    IS_ACTIVE
) VALUES (
    PRODUCT_ID_S.NEXTVAL,
    3,
    '������',
    '������',
    TO_DATE('2015/12/05 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    TO_DATE('2015/12/15 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    1,
    10000,
    0,
    NULL,
    'active'
);

INSERT INTO PICTURES (
    PRODUCT_ID,
    URL
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    'images/product-images/mixer.jpeg'
);
    
INSERT INTO PRODUCT_CATEGORY (
    PRODUCT_ID,
    CATEGORY_ID
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    16
);

INSERT INTO PRODUCTS(
    ID,
    SELLER_ID,
    NAME,
    DESCRIPTION,
    START_DATE,
    END_DATE,
    START_PRICE,
    BUYOUT_PRICE,
    CURRENT_PRICE,
    CURRENT_BUYER_ID,
    IS_ACTIVE
) VALUES (
    PRODUCT_ID_S.NEXTVAL,
    3,
    '�����������',
    '�����������',
    TO_DATE('2015/12/05 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    TO_DATE('2015/12/15 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    1,
    10000,
    0,
    NULL,
    'active'
);

INSERT INTO PICTURES (
    PRODUCT_ID,
    URL
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    'images/product-images/refregerator.jpeg'
);
    
INSERT INTO PRODUCT_CATEGORY (
    PRODUCT_ID,
    CATEGORY_ID
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    9
);

INSERT INTO PRODUCTS(
    ID,
    SELLER_ID,
    NAME,
    DESCRIPTION,
    START_DATE,
    END_DATE,
    START_PRICE,
    BUYOUT_PRICE,
    CURRENT_PRICE,
    CURRENT_BUYER_ID,
    IS_ACTIVE
) VALUES (
    PRODUCT_ID_S.NEXTVAL,
    3,
    '������',
    '������',
    TO_DATE('2015/12/05 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    TO_DATE('2015/12/15 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    1,
    10000,
    0,
    NULL,
    'active'
);

INSERT INTO PICTURES (
    PRODUCT_ID,
    URL
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    'images/product-images/toster.jpeg'
);
    
INSERT INTO PRODUCT_CATEGORY (
    PRODUCT_ID,
    CATEGORY_ID
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    15
);

INSERT INTO PRODUCTS(
    ID,
    SELLER_ID,
    NAME,
    DESCRIPTION,
    START_DATE,
    END_DATE,
    START_PRICE,
    BUYOUT_PRICE,
    CURRENT_PRICE,
    CURRENT_BUYER_ID,
    IS_ACTIVE
) VALUES (
    PRODUCT_ID_S.NEXTVAL,
    3,
    '�������',
    '�������',
    TO_DATE('2015/12/05 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    TO_DATE('2015/12/15 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    1,
    10000,
    0,
    NULL,
    'active'
);

INSERT INTO PICTURES (
    PRODUCT_ID,
    URL
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    'images/product-images/vacuum.jpeg'
);
    
INSERT INTO PRODUCT_CATEGORY (
    PRODUCT_ID,
    CATEGORY_ID
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    13
);

INSERT INTO PRODUCTS(
    ID,
    SELLER_ID,
    NAME,
    DESCRIPTION,
    START_DATE,
    END_DATE,
    START_PRICE,
    BUYOUT_PRICE,
    CURRENT_PRICE,
    CURRENT_BUYER_ID,
    IS_ACTIVE
) VALUES (
    PRODUCT_ID_S.NEXTVAL,
    3,
    '������������� ������',
    '������������� ������',
    TO_DATE('2015/12/05 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    TO_DATE('2015/12/15 21:00:00', 'yyyy/mm/dd hh24:mi:ss'),
    1,
    10000,
    0,
    NULL,
    'active'
);

INSERT INTO PICTURES (
    PRODUCT_ID,
    URL
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    'images/product-images/washer.jpeg'
);
    
INSERT INTO PRODUCT_CATEGORY (
    PRODUCT_ID,
    CATEGORY_ID
) VALUES (
    PRODUCT_ID_S.CURRVAL,
    12
);

commit;
