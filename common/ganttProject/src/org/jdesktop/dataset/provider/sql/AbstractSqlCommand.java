/*
 * AbstractSqlCommand.java
 *
 * Created on February 26, 2005, 3:04 PM
 */

package org.jdesktop.dataset.provider.sql;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jdesktop.dataset.DataCommand;
import org.jdesktop.dataset.DataRow;

/**
 *
 * @author rb156199
 */
public abstract class AbstractSqlCommand extends DataCommand {
    
    /**
     * @return a Statement, ready to execute, for the Select SQL statement.
     */
    protected abstract PreparedStatement getSelectStatement(JDBCDataConnection conn) throws Exception;
    protected abstract PreparedStatement getInsertStatement(JDBCDataConnection conn, DataRow row) throws Exception;
    protected abstract PreparedStatement getUpdateStatement(JDBCDataConnection conn, DataRow row) throws Exception;
    protected abstract PreparedStatement getDeleteStatement(JDBCDataConnection conn, DataRow row) throws Exception;
    
    protected String constructSql(String sql, Map<String,List<Integer>> indexes) {
        //replace all of the named parameters in the sql with their
        //corrosponding values. This is done by first converting the sql
        //to proper JDBC sql by inserting '?' for each and every param.
        //As this is done, a record is kept of which parameters go with
        //which indexes. Then, the parameter values are applied.
        StringBuilder buffer = new StringBuilder(sql);
        //variable containing the index of the current parameter. So,
        //for the first named param this is 0, then 1 for the next, and so on
        int paramIndex = 0;

        //iterate through the buffer looking for a colon outside of any
        //single or double quotes. This represents the beginning of a named
        //parameter
        boolean inSingleQuote = false;
        boolean inDoubleQuote = false;
        for (int i=0; i<buffer.length(); i++) {
            char c = buffer.charAt(i);
            if (c == '\'') {
                inSingleQuote = !inSingleQuote;
            } else if (c == '\"') {
                inDoubleQuote = !inDoubleQuote;
            } else if (c == ':' && !inSingleQuote && !inDoubleQuote) {
                //found the beginning of a named param. find the whole
                //name by looking from here to the first whitespace
                //character

                int firstCharIndex = i;
                i++;
                boolean found = false;
                while (!found) {
                    if (i >= buffer.length()) {
                        //I've gotten to the end of the string, so I must
                        //now have the entire variable name
                        found = true;
                    } else {
                        char next = buffer.charAt(i);
                        if (next == ' ' || next == '\n' || next == '\t' || next == '\r' || next == ',' || next == ')') {
                            found = true;
                        }
                    }
                    i++;
                }

                //ok, i-1 is the index following the last character in this sequence.
                String paramName = buffer.substring(firstCharIndex+1, i-1);

                //now that I have the name, replace it with a ? and add it
                //to the map of paramName->index values.
                buffer.replace(firstCharIndex, i-1, "?");
                if (!indexes.containsKey(paramName)) {
                    indexes.put(paramName, new ArrayList<Integer>());
                }
                List<Integer> list = indexes.get(paramName);
                list.add(paramIndex++);

                //reposition "i" to a valid value since a lot of chars were
                //just removed
                i = firstCharIndex + 1;
            }
        }
        return buffer.toString();
    }
    
//    protected PreparedStatement prepareStatement(String sql, Query q) throws SQLException {
//        //map containing the indexes for each named param
//        Map<String,List<Integer>> indexes = new HashMap<String,List<Integer>>();
//        sql = constructSql(sql, indexes);
//        PreparedStatement ps = dsc.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//
//        //now, apply the given set of parameters
//        for (String paramName : q.getParameterNames()) {
//            List<Integer> list = indexes.get(paramName);
//            if (list != null) {
//                for (int index : list) {
//                    ps.setObject(index + 1, q.getParameter(paramName));
//                }
//            }
//        }
//        return ps;
//    }
    
	public String[] getParameterNames(String[] statements) {
		//searches the statements for param names, and returns the unique set of
		//param names
		StringBuilder buffer = new StringBuilder();
		for (String s : statements) {
			buffer.append(s);
		    buffer.append("\n");
		}
		Set<String> names = new HashSet<String>();

        //iterate through the buffer looking for a colon outside of any
        //single or double quotes. This represents the beginning of a named
        //parameter
        boolean inSingleQuote = false;
        boolean inDoubleQuote = false;
        for (int i=0; i<buffer.length(); i++) {
            char c = buffer.charAt(i);
            if (c == '\'') {
                inSingleQuote = !inSingleQuote;
            } else if (c == '\"') {
                inDoubleQuote = !inDoubleQuote;
            } else if (c == ':' && !inSingleQuote && !inDoubleQuote) {
                //found the beginning of a named param. find the whole
                //name by looking from here to the first whitespace
                //character

                int firstCharIndex = i;
                i++;
                boolean found = false;
                while (!found) {
                    if (i >= buffer.length()) {
                        //I've gotten to the end of the string, so I must
                        //now have the entire variable name
                        found = true;
                    } else {
                        char next = buffer.charAt(i);
                        if (next == ' ' || next == '\n' || next == '\t' || next == '\r' || next == ',' || next == ')') {
                            found = true;
                        }
                    }
                    i++;
                }

                //ok, i-1 is the index following the last character in this sequence.
                String paramName = buffer.substring(firstCharIndex+1, i-1);
                names.add(paramName);
            }
        }
        String[] results = new String[names.size()];
        return names.toArray(results);
	}
}
