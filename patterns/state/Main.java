interface OrderState {
    void next(OrderContext ctx);
    void cancel(OrderContext ctx);
}

class NewState implements OrderState {
    public void next(OrderContext ctx) {
        System.out.println("Переход: Новый → Оплачен");
        ctx.setState(new PaidState());
    }

    public void cancel(OrderContext ctx) {
        System.out.println("Заказ отменён из состояния Новый");
        ctx.setState(new CancelledState());
    }
}

class PaidState implements OrderState {
    public void next(OrderContext ctx) {
        System.out.println("Переход: Оплачен → Доставлен");
        ctx.setState(new DeliveredState());
    }

    public void cancel(OrderContext ctx) {
        System.out.println("Нельзя отменить, уже оплачено.");
    }
}

class DeliveredState implements OrderState {
    public void next(OrderContext ctx) {
        System.out.println("Уже доставлено.");
    }

    public void cancel(OrderContext ctx) {
        System.out.println("Нельзя отменить, уже доставлено.");
    }
}

class CancelledState implements OrderState {
    public void next(OrderContext ctx) {
        System.out.println("Заказ отменён. Дальнейших переходов нет.");
    }

    public void cancel(OrderContext ctx) {
        System.out.println("Заказ уже отменён.");
    }
}

class OrderContext {
    private OrderState state;

    public OrderContext() {
        state = new NewState();
    }

    public void nextState() {
        state.next(this);
    }

    public void cancel() {
        state.cancel(this);
    }

    public void setState(OrderState state) {
        this.state = state;
    }
}

public class Main {
    public static void main(String[] args) {
        OrderContext order = new OrderContext();
        order.nextState();  // Новый → Оплачен
        order.cancel();     // Нельзя отменить
        order.nextState();  // Оплачен → Доставлен
        order.nextState();  // Уже доставлено
    }
}
