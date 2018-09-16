package problem.TSP;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class for loading and accessing the TSP instance problem
 */
public class TSPInstance {

	private String name;
	private String comment;
	private int dimension;
	private EdgeWeightType edgeWeightType;
	private EdgeWeightFormat edgeWeightFormat;
	private DistanceTable distanceTable;

	public TSPInstance() {
		super();
	}

	public TSPInstance(File file) throws IOException {
		this();
		load(file);
	}

	protected void load(File file) throws IOException {
		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new FileReader(file));
			load(reader);
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
	}

	protected void load(BufferedReader reader) throws IOException {
		String line = null;

		while ((line = reader.readLine()) != null) {
			line = line.trim();

			if (line.equals("NODE_COORD_SECTION")) {
				distanceTable = new VertexData(dimension, edgeWeightType);
				distanceTable.load(reader);
			} else if (line.equals("EDGE_WEIGHT_SECTION")) {
				distanceTable = new EdgeWeightMatrix(dimension, edgeWeightFormat);
				distanceTable.load(reader);
			} else if (line.equals("EOF")) {
				break;
			} else if (line.equals("DISPLAY_DATA_SECTION")) {
				for (int i = 0; i < this.dimension; i++) {
					line = reader.readLine();
				}
			}

			else {
				String[] tokens = line.split(":");
				String key = tokens[0].trim();
				String value = tokens[1].trim();

				if (key.equals("NAME")) {
					name = value;
				} else if (key.equals("COMMENT")) {
					if (comment == null) {
						comment = value;
					} else {
						comment = comment + "\n" + value;
					}
				} else if (key.equals("DIMENSION")) {
					dimension = Integer.parseInt(value);
				} else if (key.equals("EDGE_WEIGHT_TYPE")) {
					edgeWeightType = EdgeWeightType.valueOf(value);
				} else if (key.equals("EDGE_WEIGHT_FORMAT")) {
					edgeWeightFormat = EdgeWeightFormat.valueOf(value);
				}
			}
		}

	}

	public String getName() {
		return name;
	}

	public String getComment() {
		return comment;
	}

	public int getDimension() {
		return dimension;
	}

	public EdgeWeightType getEdgeWeightType() {
		return edgeWeightType;
	}

	public EdgeWeightFormat getEdgeWeightFormat() {
		return edgeWeightFormat;
	}

	public DistanceTable getDistanceTable() {
		return distanceTable;
	}

	public String printMatrix() {
		String result = "";

		for (int row = 0; row < dimension; row++) {
			for (int col = 0; col < dimension; col++) {
				result += String.format("%" + -8 + "s", distanceTable.getDistanceBetween(row + 1, col + 1));
			}
			result += "\n";
		}

		return result;
	}

}
