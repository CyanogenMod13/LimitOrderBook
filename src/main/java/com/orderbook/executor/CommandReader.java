package com.orderbook.executor;

import java.io.File;
import java.io.FileInputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class CommandReader {
    private static final byte[] SELL = "sell".getBytes();
    private static final byte[] BUY = "buy".getBytes();
    private static final byte[] BID = "bid".getBytes();
    private static final byte[] ASK = "ask".getBytes();
    private static final byte[] BEST_BID = "best_bid".getBytes();
    private static final byte[] BEST_ASK = "best_ask".getBytes();
    private static final byte[] SIZE = "size".getBytes();

    private static final byte CODE_U = 'u';
    private static final byte CODE_Q = 'q';
    private static final byte CODE_O = 'o';

    private static final byte CODE_STRING = 's';
    private static final byte CODE_INT = 'i';

    private File file;
    public CommandReader(File file) {
        this.file = file;
    }

    public List<Command> getCommands() {
        List<Command> list = new LinkedList<>();
        try (FileInputStream in = new FileInputStream(file)) {
            byte[] data = new byte[in.available() + 1];
            in.read(data);
            data[data.length - 1]  = '\n';

            int integerResult = 0;
            StringBuilder stringBuilder = new StringBuilder();
            Command cmd = null;

            Queue<Byte> unitCodes = new LinkedList<>();
            for (byte code : data) {
                if (code == '\r') continue;
                if (unitCodes.isEmpty()) {
                    unitCodes.add(code);
                }
                if (code == ',' || code == '\n') {
                    switch (unitCodes.remove()) {
                        case CODE_INT: cmd.addParam(integerResult); integerResult = 0; break;
                        case CODE_STRING:
                            cmd.addParam(stringBuilder.toString());
                            stringBuilder = new StringBuilder();
                            break;
                    }
                    if (code == '\n') {
                        unitCodes.clear();
                        list.add(cmd);
                    }
                } else switch (unitCodes.element()) {
                    case CODE_U:
                        cmd = new Command(Command.Type.U);
                        unitCodes.add(CODE_INT);
                        unitCodes.add(CODE_INT);
                        unitCodes.add(CODE_STRING);
                        break;
                    case CODE_Q:
                        cmd = new Command(Command.Type.Q);
                        unitCodes.add(CODE_STRING);
                        unitCodes.add(CODE_INT);
                        break;
                    case CODE_O:
                        cmd = new Command(Command.Type.O);
                        unitCodes.add(CODE_STRING);
                        unitCodes.add(CODE_INT);
                        break;
                    case CODE_INT:
                        integerResult = integerResult * 10 + code - '0';
                        break;
                    case CODE_STRING:
                        stringBuilder.append((char) code);
                        break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
