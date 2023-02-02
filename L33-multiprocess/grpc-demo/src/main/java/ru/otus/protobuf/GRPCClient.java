package ru.otus.protobuf;

import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import ru.otus.protobuf.generated.Empty;
import ru.otus.protobuf.generated.RemoteDBServiceGrpc;
import ru.otus.protobuf.generated.UserMessage;

import java.util.concurrent.CountDownLatch;

public class GRPCClient {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8190;

    public static void main(String[] args) throws InterruptedException {
        var channel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
                .usePlaintext()
                .build();

        var stub = RemoteDBServiceGrpc.newBlockingStub(channel);
        var savedUserMsg = stub.saveUser(
                UserMessage.newBuilder().setFirstName("Вася").setLastName("Кириешкин").build()
        );

        System.out.printf("Мы сохранили Васю: {id: %d, name: %s %s}%n",
                savedUserMsg.getId(), savedUserMsg.getFirstName(), savedUserMsg.getLastName());

        var allUsersIterator = stub.findAllUsers(Empty.getDefaultInstance());
        System.out.println("Конградулейшенз! Мы получили юзеров! Среди них должен найтись один Вася!");
        allUsersIterator.forEachRemaining(um ->
                System.out.printf("{id: %d, name: %s %s}%n",
                        um.getId(), um.getFirstName(), um.getLastName())
        );

        System.out.println("\n\n\nА теперь тоже самое, только асинхронно!!!\n\n");
        var latch = new CountDownLatch(1);
        var newStub = RemoteDBServiceGrpc.newStub(channel);
        newStub.findAllUsers(Empty.getDefaultInstance(), new StreamObserver<UserMessage>() {
            @Override
            public void onNext(UserMessage um) {
                System.out.printf("{id: %d, name: %s %s}%n",
                        um.getId(), um.getFirstName(), um.getLastName());
            }

            @Override
            public void onError(Throwable t) {
                System.err.println(t);
            }

            @Override
            public void onCompleted() {
                System.out.println("\n\nЯ все!");
                latch.countDown();
            }
        });

        latch.await();

        channel.shutdown();
    }
}
