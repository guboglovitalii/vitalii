from locust import task, SequentialTaskSet, HttpUser, constant_pacing, events, FastHttpUser
import sys, re, random
from config.config import cfg, logger
from utils.assertion import check_http_response
from utils.non_test_methods import open_csv_file, open_random_csv_file, generateFlightDates, processCancelRequestBody
from urllib.parse import quote_plus
from user_classes.wt_base_scenario import PurchaseFlightTicket



class Cancel(SequentialTaskSet): # класс с задачами (содержит основной сценарий)
    headers_all = {
                    'Content-Type': 'application/x-www-form-urlencoded'
                    }
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


        uc_00_getHomePage(self)
        uc_01_login(self)

    @task()
    def uc_06_Booking(self):
        
        with self.client.get(
            '/cgi-bin/welcome.pl?page=itinerary',
            name="r06_01_itinerary",
            allow_redirects=False,
            catch_response=True,
            headers=self.headers  
            # debug_stream=sys.stderr
        ) as r06_01_itinerary:
            check_http_response(r06_01_itinerary, 'Web Tours')

        with self.client.get(
            '/cgi-bin/nav.pl?page=menu&in=itinerary',
            name="r06_02_in_itinerary",
            allow_redirects=False,
            catch_response=True,
            headers=self.headers  
            # debug_stream=sys.stderr
        ) as r06_02_in_itinerary:
            check_http_response(r06_02_in_itinerary, 'Web Tours Navigation Bar')    

        
        with self.client.get(
            '/cgi-bin/itinerary.pl',
            name="r06_03_itinerary_pl",
            allow_redirects=False,
            catch_response=True,
            headers=self.headers  
            # debug_stream=sys.stderr
        ) as r06_03_itinerary_pl:
            check_http_response(r06_03_itinerary_pl, 'Flights List')


        self.flight_ids =  re.findall(r'<input type="hidden" name="flightID" value="([^"]*)"', r06_03_itinerary_pl.text)
        self.flight_nums = re.findall(r'<input type="checkbox" name="([0-9]{1,4})" value="on"', r06_03_itinerary_pl.text)

        logger.info(f"___FLIGHT_IDS: {self.flight_ids}")
        logger.info(f"___FLIGHT_NUMS: {self.flight_nums}")



    
    @task()
    def uc_07_DeleteOneTicket(self):
        if not self.flight_ids or not self.flight_nums:
            logger.warning("Нет билетов для удаления")
            return
   
        data_r07_01 = processCancelRequestBody(self.flight_ids, self.flight_nums)

        logger.info(f"____DATA_R07_01: {data_r07_01}")

        with self.client.post(
            '/cgi-bin/itinerary.pl',
            name="r07_01_itinerary_pl_delete",
            allow_redirects=False,
            catch_response=True,
            headers={
                    'Content-Type': 'application/x-www-form-urlencoded'
                    },
            data=data_r07_01  
            # debug_stream=sys.stderr
        ) as r07_01_itinerary_pl_delete:
            check_http_response(r07_01_itinerary_pl_delete, 'Flights List')

class WebToursCancelUserClass(HttpUser): # юзер-класс, принимающий в себя основные параметры теста
    wait_time = constant_pacing(cfg.webtours_cancel.pacing)
    host = cfg.url
    

    tasks = [Cancel]