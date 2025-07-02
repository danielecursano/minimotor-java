package it.dani.minimotorways.model.visitor;

import it.dani.minimotorways.model.Color;
import it.dani.minimotorways.model.GameMap;
import it.dani.minimotorways.model.building.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class PathVisitor implements Visitor {
    private List<Integer> matrix = new ArrayList<>();

    public PathVisitor() {

    }

    public int getPath(int start, Color color) {
        int rows = GameMap.getRows();
        int cols = GameMap.getCols();

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        boolean[] visited = new boolean[matrix.size()];
        int[] parent = new int[matrix.size()];

        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        visited[start] = true;
        parent[start] = -1;

        int dest = -1;

        while (!queue.isEmpty()) {
            int cur = queue.poll();
            int r = cur / cols;
            int c = cur % cols;

            if (matrix.get(cur) == color.getId()) {
                dest = cur;
                break;
            }

            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];
                if (nr >= 0 && nr < rows && nc >= 0 && nc < cols) {
                    int ni = nr * cols + nc;
                    if (!visited[ni] && (matrix.get(ni) == 1 || matrix.get(ni) == color.getId())) {
                        visited[ni] = true;
                        parent[ni] = cur;
                        queue.add(ni);
                    }
                }
            }
        }

        // No destination found
        if (dest == -1) return -1;

        // Reconstruct the path: go from dest back to start
        int current = dest;
        int prev = dest;

        while (parent[current] != -1) {
            prev = current;
            current = parent[current];
            if (current == start) {
                return prev; // The next position after start
            }
        }

        return -1; // Shouldn't happen unless something went wrong
    }


    @Override
    public void visit(Road road) {
        matrix.add(road.isFree() ? 1 : 0);
    }

    @Override
    public void visit(House house) {
        matrix.add(0);
    }

    @Override
    public void visit(SkyScraper skyscraper) {
        matrix.add(0);
    }

    @Override
    public void visit(Destination destination) {
        matrix.add(destination.getColor().getId());
    }

    @Override
    public void visit(EmptyCell emptyCell) {
        matrix.add(0);
    }

    @Override
    public void visit(Building building) {

    }

    public void setZero(int i) {
        matrix.set(i, 0);
    }

    public void setOne(int i) {
        matrix.set(i, 1);
    }
}
