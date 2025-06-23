import java.util.ArrayList;
import java.util.List;

public class ObserverPatternDemo {
    public static void main(String[] args) {
        // Создаем метеостанцию (субъект)
        WeatherStation weatherStation = new WeatherStation();
        
        // Создаем устройства отображения (наблюдатели)
        CurrentConditionsDisplay currentDisplay = new CurrentConditionsDisplay();
        StatisticsDisplay statisticsDisplay = new StatisticsDisplay();
        ForecastDisplay forecastDisplay = new ForecastDisplay();
        
        // Регистрируем наблюдателей
        weatherStation.registerObserver(currentDisplay);
        weatherStation.registerObserver(statisticsDisplay);
        weatherStation.registerObserver(forecastDisplay);
        
        // Имитируем изменение погоды
        System.out.println("=== Первое обновление данных ===");
        weatherStation.setMeasurements(25, 65, 1013);
        
        System.out.println("\n=== Второе обновление данных ===");
        weatherStation.setMeasurements(27, 70, 1010);
        
        // Удаляем один наблюдатель
        weatherStation.removeObserver(statisticsDisplay);
        
        System.out.println("\n=== Третье обновление данных (без статистики) ===");
        weatherStation.setMeasurements(22, 90, 1015);
    }
}

// 1. Интерфейс субъекта
interface Subject {
    void registerObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers();
}

// 2. Интерфейс наблюдателя
interface Observer {
    void update(float temperature, float humidity, float pressure);
}

// 3. Конкретный субъект (метеостанция)
class WeatherStation implements Subject {
    private final List<Observer> observers = new ArrayList<>();
    private float temperature;
    private float humidity;
    private float pressure;
    
    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }
    
    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }
    
    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(temperature, humidity, pressure);
        }
    }
    
    public void setMeasurements(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        measurementsChanged();
    }
    
    private void measurementsChanged() {
        notifyObservers();
    }
}

// 4. Конкретные наблюдатели

// Дисплей текущих условий
class CurrentConditionsDisplay implements Observer {
    private float temperature;
    private float humidity;
    
    @Override
    public void update(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        display();
    }
    
    public void display() {
        System.out.println("[Текущие условия] " + temperature + "°C, " + humidity + "% влажности");
    }
}

// Дисплей статистики
class StatisticsDisplay implements Observer {
    private float maxTemp = Float.MIN_VALUE;
    private float minTemp = Float.MAX_VALUE;
    private float tempSum = 0.0f;
    private int numReadings = 0;
    
    @Override
    public void update(float temperature, float humidity, float pressure) {
        tempSum += temperature;
        numReadings++;
        
        if (temperature > maxTemp) maxTemp = temperature;
        if (temperature < minTemp) minTemp = temperature;
        
        display();
    }
    
    public void display() {
        System.out.println("[Статистика] Средняя: " + (tempSum/numReadings) + 
                         "°C, Макс: " + maxTemp + "°C, Мин: " + minTemp + "°C");
    }
}

// Дисплей прогноза
class ForecastDisplay implements Observer {
    private float lastPressure;
    private float currentPressure = 1013;
    
    @Override
    public void update(float temperature, float humidity, float pressure) {
        lastPressure = currentPressure;
        currentPressure = pressure;
        display();
    }
    
    public void display() {
        System.out.print("[Прогноз] ");
        if (currentPressure > lastPressure) {
            System.out.println("Улучшение погоды!");
        } else if (currentPressure == lastPressure) {
            System.out.println("Погода не изменится");
        } else {
            System.out.println("Ожидаются осадки");
        }
    }
}