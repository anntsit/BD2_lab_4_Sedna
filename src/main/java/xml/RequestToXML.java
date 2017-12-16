package xml;
import net.sf.saxon.xqj.SaxonXQDataSource;

import javax.xml.xquery.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class RequestToXML {
    public XQDataSource ds;
    public XQConnection conn;

    public RequestToXML() throws XQException {
        ds = new SaxonXQDataSource();
        conn = ds.getConnection();
    }

    public String execute(String query) throws FileNotFoundException, XQException{
        List<String> res = new ArrayList<>();
        XQPreparedExpression exp = conn.prepareExpression(query);
        XQResultSequence result = exp.executeQuery();
        while (result.next()) {
            res.add(result.getItemAsString(null));
        }
        return  res.toString();
    }

    public String getListOfBooks() throws FileNotFoundException, XQException{
        String query ="let $x := doc('xml/booksLibrary.xml')/all_books/book return $x/data(title) ";
        return execute(query);
    }
    public String getListOfAuthors() throws FileNotFoundException, XQException{
        String query ="for $x in distinct-values(doc('xml/booksLibrary.xml')/all_books/book/author) order by $x return $x";
        return execute(query);
    }
    public String getListOfGenres() throws FileNotFoundException, XQException{
        String query ="for $x in distinct-values(doc('xml/booksLibrary.xml')/all_books/book/genre) order by $x return $x";
        return execute(query);
    }
    public String getListOfCities() throws FileNotFoundException, XQException{
        String query ="for $x in distinct-values(doc('xml/booksLibrary.xml')/all_books/book/city) order by $x return $x";
        return execute(query);
    }
    public String getListOfPublishers() throws FileNotFoundException, XQException{
        String query ="for $x in distinct-values(doc('xml/booksLibrary.xml')/all_books/book/publisher) order by $x return $x";
        return execute(query);
    }
    public String getListOfBooksByAuthor(String author) throws FileNotFoundException, XQException{
        String query ="for $x in doc('xml/booksLibrary.xml')/all_books/book where $x/author = '"+
                author+"' return $x/data(title)";
        return execute(query);
    }
    public String getListOfBooksByPublisher(String publisher) throws FileNotFoundException, XQException{
        String query ="for $x in doc('xml/booksLibrary.xml')/all_books/book where $x/publisher = '"
                +publisher+"' return $x/data(title)";
        return execute(query);
    }
    public String getListOfBooksByGenre(String genre) throws FileNotFoundException, XQException{
        String query ="for $x in doc('xml/booksLibrary.xml')/all_books/book where $x/genre = '"
                +genre+"' return $x/data(title)";
        return execute(query);
    }
    public String getListOfBookByDate(String start,String end) throws FileNotFoundException, XQException{
        String query ="for $x in doc('xml/booksLibrary.xml')/all_books/book where $x/year > "+
                start +" and $x/year < "+end+" order by $x/year " +
                " return $x/data(title)";
        return execute(query);
    }
    public String getListOfBookByPrice(String start,String end) throws FileNotFoundException, XQException{
        String query ="for $x in doc('xml/booksLibrary.xml')/all_books/book where $x/price > "+
                start +" and $x/price < "+end+" order by $x/price " +
                " return $x/data(title)";
        return execute(query);
    }
    public String getCountOfBooksByAuthor(String author) throws FileNotFoundException, XQException{
        String query ="let $c := 0 return count(for $x in doc('xml/booksLibrary.xml')/all_books/book " +
                "where $x/author='"+author+"' return $x/data(author))";
        return execute(query);
    }
    public String getTotalPriceOfBooksByAuthor(String author) throws FileNotFoundException, XQException{
        String query ="let $c := 0 return sum(for $x in doc('xml/booksLibrary.xml')/all_books/book " +
                "where $x/author='"+author+"' return $x/data(price))";
        return execute(query);
    }
    public String getCountOfSolidBooks() throws FileNotFoundException, XQException{
        String query ="let $c := 0 return count(for $x in doc('xml/booksLibrary.xml')/all_books/book " +
                "where $x/cover='solid' return $x/cover)";
        return execute(query);
    }
    public String getCountOfSoftBooks() throws FileNotFoundException, XQException{
        String query ="let $c := 0 return count(for $x in doc('xml/booksLibrary.xml')/all_books/book " +
                "where $x/cover='soft' return $x/cover)";
        return execute(query);
    }
}
