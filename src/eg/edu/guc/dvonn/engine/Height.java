package eg.edu.guc.dvonn.engine;

import java.util.ArrayList;

public class Height {
	private ArrayList<Integer> x;
	private boolean b;

	public boolean isB() {
		return b;
	}

	public void setB(boolean b) {
		this.b = b;
	}

	public Height() {
		x = new ArrayList<Integer>();
		b = false;
	}

	public void delete() {
		x.clear();
	}

	public int size() {
		return x.size();
	}

	public int getHeight() {
		int count = 0;
		for (int i = 0; i < x.size(); i++) {
			if (x.get(i) != 0) {
				count++;
			}
		}
		return count;
	}

	public void add(int v) {
		x.add(v);
	}

	public int getLast() {
		if (x.size() != 0) {
			return x.get(x.size() - 1);
		}
		return -1;
	}

	public int getFirst() {
		if (x.size() != 0) {
			return x.get(0);
		}
		return -1;
	}

	public int getI(int index) {
		return x.get(index);
	}
}