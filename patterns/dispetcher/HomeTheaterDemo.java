public class HomeTheaterDemo {

    public static void main(String[] args) {
        // Инициализируем фасад
        HomeTheaterFacade theater = new HomeTheaterFacade(
            new Amplifier(), 
            new Tuner(), 
            new DvdPlayer(),
            new Projector(), 
            new Screen(), 
            new TheaterLights(), 
            new PopcornPopper()
        );

        // Используем упрощённый интерфейс фасада
        System.out.println("===== Запускаем просмотр фильма =====");
        theater.watchMovie("Интерстеллар");
        
        System.out.println("\n===== Завершаем просмотр =====");
        theater.endMovie();
    }

    // Фасад (диспетчер) домашнего кинотеатра
    static class HomeTheaterFacade {
        private final Amplifier amp;
        private final Tuner tuner;
        private final DvdPlayer dvd;
        private final Projector projector;
        private final TheaterLights lights;
        private final Screen screen;
        private final PopcornPopper popper;

        public HomeTheaterFacade(Amplifier amp, Tuner tuner, DvdPlayer dvd, 
                                Projector projector, Screen screen, 
                                TheaterLights lights, PopcornPopper popper) {
            this.amp = amp;
            this.tuner = tuner;
            this.dvd = dvd;
            this.projector = projector;
            this.screen = screen;
            this.lights = lights;
            this.popper = popper;
        }

        // Упрощённый интерфейс для просмотра фильма
        public void watchMovie(String movie) {
            System.out.println("Подготавливаем домашний кинотеатр...");
            popper.on();
            popper.pop();
            lights.dim(10);
            screen.down();
            projector.on();
            projector.setInput("DVD");
            projector.wideScreenMode();
            amp.on();
            amp.setDvd(dvd);
            amp.setSurroundSound();
            amp.setVolume(5);
            dvd.on();
            dvd.play(movie);
            System.out.println("Наслаждайтесь просмотром '" + movie + "'!");
        }

        // Упрощённый интерфейс для завершения просмотра
        public void endMovie() {
            System.out.println("Выключаем домашний кинотеатр...");
            popper.off();
            lights.on();
            screen.up();
            projector.off();
            amp.off();
            dvd.stop();
            dvd.eject();
            dvd.off();
            System.out.println("Кинотеатр успешно выключен!");
        }
    }

    // Классы компонентов домашнего кинотеатра
    static class Amplifier {
        public void on() { System.out.println("Усилитель включён"); }
        public void off() { System.out.println("Усилитель выключен"); }
        public void setDvd(DvdPlayer dvd) { System.out.println("Усилитель: источник - DVD"); }
        public void setSurroundSound() { System.out.println("Усилитель: режим surround sound (5.1)"); }
        public void setVolume(int level) { System.out.println("Усилитель: громкость " + level); }
    }

    static class Tuner {
        public void on() { System.out.println("Тюнер включён"); }
        public void off() { System.out.println("Тюнер выключен"); }
    }

    static class DvdPlayer {
        public void on() { System.out.println("DVD-плеер включён"); }
        public void off() { System.out.println("DVD-плеер выключен"); }
        public void play(String movie) { System.out.println("DVD: воспроизведение фильма '" + movie + "'"); }
        public void stop() { System.out.println("DVD: остановка воспроизведения"); }
        public void eject() { System.out.println("DVD: извлечение диска"); }
    }

    static class Projector {
        public void on() { System.out.println("Проектор включён"); }
        public void off() { System.out.println("Проектор выключен"); }
        public void setInput(String source) { System.out.println("Проектор: источник - " + source); }
        public void wideScreenMode() { System.out.println("Проектор: режим широкого экрана (16:9)"); }
    }

    static class Screen {
        public void down() { System.out.println("Экран опущен"); }
        public void up() { System.out.println("Экран поднят"); }
    }

    static class TheaterLights {
        public void on() { System.out.println("Освещение: 100% яркость"); }
        public void dim(int percent) { System.out.println("Освещение: уменьшено до " + percent + "%"); }
    }

    static class PopcornPopper {
        public void on() { System.out.println("Попкорн-машина включена"); }
        public void off() { System.out.println("Попкорн-машина выключена"); }
        public void pop() { System.out.println("Попкорн-машина: готовим попкорн!"); }
    }
}