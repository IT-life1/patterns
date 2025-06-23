public class CommandPatternDemo {
    public static void main(String[] args) {
        // Создаем устройства (получатели команд)
        Light livingRoomLight = new Light("Гостиная");
        Stereo stereo = new Stereo();
        TV tv = new TV();
        
        // Создаем команды
        Command lightOn = new LightOnCommand(livingRoomLight);
        Command lightOff = new LightOffCommand(livingRoomLight);
        Command stereoOn = new StereoOnCommand(stereo);
        Command stereoOff = new StereoOffCommand(stereo);
        Command tvOn = new TVOnCommand(tv);
        Command tvOff = new TVOffCommand(tv);
        
        // Создаем пульт (инициатор)
        RemoteControl remote = new RemoteControl();
        
        // Настраиваем кнопки
        remote.setCommand(0, lightOn, lightOff);
        remote.setCommand(1, stereoOn, stereoOff);
        remote.setCommand(2, tvOn, tvOff);
        
        System.out.println("--- Тестируем пульт ---");
        remote.onButtonPressed(0);  // Включаем свет
        remote.onButtonPressed(1);  // Включаем стерео
        remote.onButtonPressed(2);  // Включаем TV
        
        System.out.println("\n--- Отменяем последние действия ---");
        remote.undo();  // Выключаем TV
        remote.undo();  // Выключаем стерео
        
        System.out.println("\n--- Тестируем выключение ---");
        remote.offButtonPressed(0); // Выключаем свет
        remote.offButtonPressed(1); // Пытаемся выключить стерео (уже выключено)
        
        System.out.println("\n--- Макрокоманда 'Вечерний режим' ---");
        Command[] eveningOn = {lightOn, stereoOn, tvOn};
        Command[] eveningOff = {lightOff, stereoOff, tvOff};
        Command eveningMode = new MacroCommand(eveningOn, eveningOff);
        
        remote.setCommand(3, eveningMode, new NoCommand());
        
        System.out.println("Включаем вечерний режим:");
        remote.onButtonPressed(3);
        
        System.out.println("\nВыключаем вечерний режим:");
        remote.offButtonPressed(3);
    }
}

// 1. Интерфейс команды
interface Command {
    void execute();
    void undo();
}

// 2. Конкретные команды

// Команда включения света
class LightOnCommand implements Command {
    private final Light light;
    
    public LightOnCommand(Light light) {
        this.light = light;
    }
    
    @Override
    public void execute() {
        light.on();
    }
    
    @Override
    public void undo() {
        light.off();
    }
}

// Команда выключения света
class LightOffCommand implements Command {
    private final Light light;
    
    public LightOffCommand(Light light) {
        this.light = light;
    }
    
    @Override
    public void execute() {
        light.off();
    }
    
    @Override
    public void undo() {
        light.on();
    }
}

// Команда включения стерео
class StereoOnCommand implements Command {
    private final Stereo stereo;
    
    public StereoOnCommand(Stereo stereo) {
        this.stereo = stereo;
    }
    
    @Override
    public void execute() {
        stereo.on();
        stereo.setVolume(15);
    }
    
    @Override
    public void undo() {
        stereo.off();
    }
}

// Команда выключения стерео
class StereoOffCommand implements Command {
    private final Stereo stereo;
    
    public StereoOffCommand(Stereo stereo) {
        this.stereo = stereo;
    }
    
    @Override
    public void execute() {
        stereo.off();
    }
    
    @Override
    public void undo() {
        stereo.on();
        stereo.setVolume(15);
    }
}

// Команда включения TV
class TVOnCommand implements Command {
    private final TV tv;
    
    public TVOnCommand(TV tv) {
        this.tv = tv;
    }
    
    @Override
    public void execute() {
        tv.on();
        tv.setChannel(5);
    }
    
    @Override
    public void undo() {
        tv.off();
    }
}

// Команда выключения TV
class TVOffCommand implements Command {
    private final TV tv;
    
    public TVOffCommand(TV tv) {
        this.tv = tv;
    }
    
    @Override
    public void execute() {
        tv.off();
    }
    
    @Override
    public void undo() {
        tv.on();
        tv.setChannel(5);
    }
}

// Пустая команда (null-object pattern)
class NoCommand implements Command {
    @Override
    public void execute() {
        System.out.println("Кнопка не настроена");
    }
    
    @Override
    public void undo() {
        System.out.println("Отмена недоступна");
    }
}

// Макрокоманда (последовательность команд)
class MacroCommand implements Command {
    private final Command[] onCommands;
    private final Command[] offCommands;
    
    public MacroCommand(Command[] onCommands, Command[] offCommands) {
        this.onCommands = onCommands;
        this.offCommands = offCommands;
    }
    
    @Override
    public void execute() {
        for (Command command : onCommands) {
            command.execute();
        }
    }
    
    @Override
    public void undo() {
        for (Command command : offCommands) {
            command.execute();
        }
    }
}

// 3. Получатели команд (устройства)

// Свет
class Light {
    private final String location;
    private boolean on;
    
    public Light(String location) {
        this.location = location;
    }
    
    public void on() {
        on = true;
        System.out.println("Свет в " + location + " включен");
    }
    
    public void off() {
        on = false;
        System.out.println("Свет в " + location + " выключен");
    }
    
    public boolean isOn() {
        return on;
    }
}

// Стереосистема
class Stereo {
    private boolean on;
    private int volume;
    
    public void on() {
        on = true;
        System.out.println("Стерео включено");
    }
    
    public void off() {
        on = false;
        System.out.println("Стерео выключено");
    }
    
    public void setVolume(int volume) {
        this.volume = volume;
        System.out.println("Громкость установлена на " + volume);
    }
    
    public boolean isOn() {
        return on;
    }
}

// Телевизор
class TV {
    private boolean on;
    private int channel;
    
    public void on() {
        on = true;
        System.out.println("Телевизор включен");
    }
    
    public void off() {
        on = false;
        System.out.println("Телевизор выключен");
    }
    
    public void setChannel(int channel) {
        this.channel = channel;
        System.out.println("Канал установлен на " + channel);
    }
    
    public boolean isOn() {
        return on;
    }
}

// 4. Инициатор (пульт управления)
class RemoteControl {
    private final Command[] onCommands;
    private final Command[] offCommands;
    private Command undoCommand;
    
    public RemoteControl() {
        onCommands = new Command[7];
        offCommands = new Command[7];
        
        // Инициализация пустыми командами
        Command noCommand = new NoCommand();
        for (int i = 0; i < 7; i++) {
            onCommands[i] = noCommand;
            offCommands[i] = noCommand;
        }
        undoCommand = noCommand;
    }
    
    public void setCommand(int slot, Command onCommand, Command offCommand) {
        onCommands[slot] = onCommand;
        offCommands[slot] = offCommand;
    }
    
    public void onButtonPressed(int slot) {
        onCommands[slot].execute();
        undoCommand = onCommands[slot];
    }
    
    public void offButtonPressed(int slot) {
        offCommands[slot].execute();
        undoCommand = offCommands[slot];
    }
    
    public void undo() {
        System.out.println("--- Отмена ---");
        undoCommand.undo();
    }
}