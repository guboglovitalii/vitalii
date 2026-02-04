from locust import task, SequentialTaskSet, HttpUser, constant_pacing, events, FastHttpUser
import sys, re, random
from config.config import cfg, logger
from utils.assertion import check_http_response
from utils.non_test_methods import open_csv_file, open_random_csv_file, generateFlightDates
from urllib.parse import quote_plus



class PurchaseFlightTicket(SequentialTaskSet): # класс с задачами (содержит основной сценарий)
    def on_start(self):
        self.user_data_csv_file = './test_data/user_data.csv'
        self.flight_details_csv_file = './test_data/flight_details.csv'
        self.cards_numbers_csv_file = './test_data/cards_numbers.csv'
        self.first_last_csv_file = './test_data/first_last.csv'
        self.users_data = open_csv_file(self.user_data_csv_file)
        self.random_users_data = open_random_csv_file(self.user_data_csv_file)
        self.random_flight_details = open_random_csv_file(self.flight_details_csv_file)
        self.random_cards_numbers = open_random_csv_file(self.cards_numbers_csv_file)
        self.random_first_last = open_random_csv_file(self.first_last_csv_file)

        logger.info(f"____FLIGHT DETAILS OPEN CSV: {self.random_flight_details}")

        
    @task()
    def uc_00_getHomePage(self):
        r00_01_WebTours = self.client.get(
            '/WebTours/',
            name="r00_01_response",
            allow_redirects=False,
            headers={
                'sec-ch-ua': 'Not(A:Brand";v="8", "Chromium";v="144", "Microsoft Edge";v="144',
                'sec-ch-ua-mobile': '?0'
            },
            # debug_stream=sys.stderr
        )

        with self.client.get(
            '/WebTours/header.html',
            name="r00_02_header_html",
            allow_redirects=False,
            # debug_stream=sys.stderr,
            catch_response=True
        ) as r00_02_header_html:
            check_http_response(r00_02_header_html, 'images/webtours.png')


        with self.client.get(
            '/cgi-bin/welcome.pl?signOff=true',
            name="r00_03_welcome_pl",
            allow_redirects=False,
            catch_response=True
            # debug_stream=sys.stderr
        ) as r00_03_welcome_pl:
            check_http_response(r00_03_welcome_pl, 'Web Tours')

        with self.client.get(
            '/cgi-bin/nav.pl?in=home',
            name="r00_04_nav_pl",
            allow_redirects=False,
            catch_response=True
            # debug_stream=sys.stderr
        ) as r00_04_nav_pl:
            check_http_response(r00_04_nav_pl, 'Web Tours Navigation Bar')

            self.user_session = re.search(r"name=\"userSession\" value=\"(.*)\"\/", r00_04_nav_pl.text).group(1)

        with self.client.get(
            '/WebTours/home.html',
            name="r00_05_home_html",
            allow_redirects=False,
            catch_response=True
            # debug_stream=sys.stderr
        ) as r00_05_home_html:
            check_http_response(r00_05_home_html, 'Welcome to the Web Tours site.')
        # logger.info(f"Статус ответа: {r001_01_response.status_code}, Тело ответа: {r001_01_response.text}")
        # print(f"Статус ответа: {r001_01_response.status_code}, Тело ответа: {r001_01_response.text}") 

    @task()
    def uc_01_login(self):
        self.users_row = next(self.users_data)
        self.random_users_row = random.choice(self.random_users_data)
        self.login = self.random_users_row["name"]
        self.password = self.random_users_row["password"]
        self.headers = {
                'Content-Type': 'application/x-www-form-urlencoded'
                }


        self.body_r01_login_pl = f'userSession={self.user_session}&username={self.login}&password={self.password}&login.x=73&login.y=12&JSFormSubmit=off'
        # print(f"_______BODY LOGIN: {self.body_r01_login_pl}")
        # logger.info(f"_______BODY LOGIN: {self.body_r01_login_pl}")

        with self.client.post(
            '/cgi-bin/login.pl',
            name="r01_01_login",
            allow_redirects=False,
            catch_response=True,
            headers=self.headers,
            data=self.body_r01_login_pl    
            # debug_stream=sys.stderr
        ) as r01_01_login:
            check_http_response(r01_01_login, 'Web Tours')


        with self.client.get(
            '/cgi-bin/nav.pl?page=menu&in=home',
            name="r01_02_nav_pl",
            allow_redirects=False,
            catch_response=True
            # debug_stream=sys.stderr
        ) as r01_02_nav_pl:
            check_http_response(r01_02_nav_pl, 'Web Tours Navigation Bar')


        with self.client.get(
            '/cgi-bin/login.pl?intro=true',
            name="r01_03_intro",
            allow_redirects=False,
            catch_response=True
            # debug_stream=sys.stderr
        ) as r01_03_intro:
            check_http_response(r01_03_intro, 'Welcome to Web Tours')



    @task()
    def uc_02_Check_Flihts(self):
        
       
        with self.client.get(
            '/cgi-bin/welcome.pl?page=search',
            name="r02_01_welkome_pl",
            allow_redirects=False,
            catch_response=True    
            # debug_stream=sys.stderr
        ) as r02_01_welkome_pl:
            check_http_response(r02_01_welkome_pl, 'Web Tours')


        with self.client.get(
            '/cgi-bin/nav.pl?page=menu&in=flights',
            name="r02_02_menu_flights",
            allow_redirects=False,
            catch_response=True    
            # debug_stream=sys.stderr
        ) as r02_02_menu_flights:
            check_http_response(r02_02_menu_flights, 'Web Tours Navigation Bar')


        with self.client.get(
            '/cgi-bin/reservations.pl?page=welcome',
            name="r02_03_welcome",
            allow_redirects=False,
            catch_response=True    
            # debug_stream=sys.stderr
        ) as r02_03_welcome:
            check_http_response(r02_03_welcome, 'Flight Selections')



    @task()
    def uc_03_FindFlight(self):
        self.random_flight_row = random.choice(self.random_flight_details)
        logger.info(f'______FLIGHTS_ROW: {self.random_flight_row}')
        self.depart = self.random_users_row["depart"]
        self.arrive = self.random_users_row["arrive"]
        self.seatType = self.random_flight_row["seat_type"]
        self.seatPref = self.random_flight_row["seat_pref"]

        self.dates_dict = generateFlightDates()
        logger.info(f"_____DATES_DICKT: {self.dates_dict}")
        self.depart_date = self.dates_dict["depart_date"]
        self.return_date = self.dates_dict["return_date"]

        data_r03_01 = f'advanceDiscount=0&depart={self.depart}&departDate={self.depart_date}&arrive={self.arrive}&returnDate={self.return_date}&numPassengers=1&seatPref={self.seatPref}&seatType={self.seatType}&findFlights.x=63&findFlights.y=7&.cgifields=roundtrip&.cgifields=seatType&.cgifields=seatPref'
        # logger.info(f"____DATA_R03_01: {data_r03_01}")

        with self.client.post(
            '/cgi-bin/reservations.pl',
            name="r03_01_reservations",
            allow_redirects=False,
            catch_response=True,
            headers=self.headers,
            data=data_r03_01 
            # debug_stream=sys.stderr
        ) as r03_01_reservations:
            check_http_response(r03_01_reservations, 'Flight Selections')

        self.dict_outboundFlight = re.findall(r'<input type="radio" name="outboundFlight" value="([^"]*)"', r03_01_reservations.text)
        logger.info(f"___DICT_OUTBOUNFLIGHT: {self.dict_outboundFlight}")



    @task()
    def uc_04_ChooseFlightOptions(self):
        self.random_flight_row = random.choice(self.random_flight_details)

        self.seatType = self.random_flight_row["seat_type"]
        self.seatPref = self.random_flight_row["seat_pref"]
        self.outboundFlight = quote_plus(random.choice(self.dict_outboundFlight))

       
        data_r04_01 = f'outboundFlight={self.outboundFlight}&numPassengers=1&advanceDiscount=0&seatType={self.seatType}&seatPref={self.seatPref}&reserveFlights.x=47&reserveFlights.y=14'
        logger.info(f"____DATA_R04_01: {data_r04_01}")
        with self.client.post(
            '/cgi-bin/reservations.pl',
            name="r04_01_reservations",
            allow_redirects=False,
            catch_response=True,
            headers=self.headers,
            data=data_r04_01  
            # debug_stream=sys.stderr
        ) as r04_01_reservations:
            check_http_response(r04_01_reservations, 'Flight Reservation')


    @task()
    def uc_05_FlightRegistered(self):
        self.random_flight_row = random.choice(self.random_flight_details)

        self.seatType = self.random_flight_row["seat_type"]
        self.seatPref = self.random_flight_row["seat_pref"]
        self.outboundFlight = quote_plus(random.choice(self.dict_outboundFlight))

        self.random_card_row = random.choice(self.random_cards_numbers)
        self.creditCard = self.random_card_row["creditCard"]
        self.expDate = quote_plus(self.random_card_row["expDate"])

        self.random_name_row = random.choice(self.random_first_last)
        self.firstName = self.random_name_row["firstName"]
        self.lastName = self.random_name_row["lastName"]

       
        data_r05_01 = f'firstName={self.firstName}&lastName={self.lastName}&address1=&address2=&pass1={self.firstName}+{self.lastName}&creditCard={self.creditCard}&expDate={self.expDate}&saveCC=on&oldCCOption=on&numPassengers=1&seatType={self.seatType}&seatPref={self.seatPref}&outboundFlight={self.outboundFlight}&advanceDiscount=0&returnFlight=&JSFormSubmit=off&buyFlights.x=68&buyFlights.y=5&.cgifields=saveCC'
        logger.info(f"____DATA_R05_01: {data_r05_01}")
        with self.client.post(
            '/cgi-bin/reservations.pl',
            name="r05_01_reservations",
            allow_redirects=False,
            catch_response=True,
            headers=self.headers,
            data=data_r05_01  
            # debug_stream=sys.stderr
        ) as r05_01_reservations:
            check_http_response(r05_01_reservations, 'Reservation Made!')   



    
 

        


class WebToursBaseUserClass(FastHttpUser): # юзер-класс, принимающий в себя основные параметры теста
    wait_time = constant_pacing(cfg.pacing)
    host = cfg.url
    tasks = [PurchaseFlightTicket]