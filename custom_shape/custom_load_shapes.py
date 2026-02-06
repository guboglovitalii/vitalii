# from locust import LoadTestShape
# from config.config import cfg, logger


# class CustomLoadShape(LoadTestShape):
#     """
#         Здесь должны быть описаны типы нагрузки с помощью stages
#     """
#     def __init__(self):
#         super().__init__()
#         match cfg.loadshape_type:
#             case "baseline":
#                 self.stages = [
#                     {"duration": 600, "users": 4, "spawn_rate": 5}
#                 ]
#             case "stages":
#                 self.stages = [
#                     {"duration": 600, "users": 3, "spawn_rate": 5},
#                     {"duration": 1200, "users": 4, "spawn_rate": 5},
#                     {"duration": 1800, "users": 4, "spawn_rate": 5},
#                     {"duration": 2400, "users": 3, "spawn_rate": 5},
#                     {"duration": 3000, "users": 5, "spawn_rate": 5},
#                 ]

#     def tick(self):  # стандартная функция локаста, взятая из документации
#         run_time = self.get_run_time()

#         for stage in self.stages:
#             if run_time < stage["duration"]:
#                 tick_data = (stage["users"], stage["spawn_rate"])
#                 return tick_data

#         return None


from locust import LoadTestShape
from config.config import cfg, logger


def generate_stages(num_stages, step_duration, users_list, spawn_rate=5):
    """
    Генерация списка stages для Locust LoadShape.
    :param num_stages: количество ступеней
    :param step_duration: длительность каждой ступени (в секундах)
    :param users_list: список пользователей по ступеням
    :param spawn_rate: скорость появления пользователей
    :return: список словарей stages
    """
    stages = []
    duration = 0
    for i in range(num_stages):
        duration += step_duration
        stages.append({
            "duration": duration,
            "users": users_list[i % len(users_list)],
            "spawn_rate": spawn_rate
        })
    return stages


class CustomLoadShape(LoadTestShape):
    """
    Типы нагрузки с помощью stages
    """
    def __init__(self):
        super().__init__()
        match cfg.loadshape_type:
            case "baseline":
                self.stages = [
                    {"duration": 600, "users": 4, "spawn_rate": 5}
                ]
            case "stages":
                # 5 ступеней по 600 секунд, пользователи от 3 до 5
                self.stages = generate_stages(
                    num_stages=5,
                    step_duration=600,
                    users_list=[3, 4, 4, 3, 5],
                    spawn_rate=5
                )

    def tick(self):
        run_time = self.get_run_time()
        for stage in self.stages:
            if run_time < stage["duration"]:
                logger.info(
                    f"Stage active: duration={stage['duration']}s, "
                    f"users={stage['users']}, spawn_rate={stage['spawn_rate']}, "
                    f"run_time={run_time:.1f}s"
                )
                return stage["users"], stage["spawn_rate"]
        return None
