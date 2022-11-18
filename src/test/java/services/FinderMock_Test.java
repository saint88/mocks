package services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import data.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import service.api.IApiService;
import service.finder.Finder;
import service.finder.IFind;
import service.finder.TestFinder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@RunWith(MockitoJUnitRunner.class)
public class FinderMock_Test {

  @Mock
  private IApiService mockApiService;

  @Test
  public void test_finder_real_objects() {
    List<Student> students = new ArrayList<>();
    students.add(new Student("Pasha", 33));
    students.add(new Student("Sasha", 40));

    IFind<Student> finder = new Finder<>(students, (Student student) ->  student.getName().equals("Pasha"));
    Student result = finder.search();

    assertThat(result).extracting(Student::getName).isEqualTo("Pasha");
    assertThat(result).extracting(Student::getAge).isEqualTo(33);
  }

  @Test
  public void test_finder_dummy_mock_object() {
    List mockStudent = Mockito.mock(List.class);

    IFind<Student> finder = new Finder<>(mockStudent, student -> true);
    Student result = finder.search();

    assertThat(result).as("").isNull();
  }

  @Test
  public void test_finder_stub() {
    Student expectedStudent = new Student("Sasha", 46);

    IFind<Student> mockFinder = Mockito.mock(Finder.class);

    Mockito
        .when(mockFinder.search())
        .thenReturn(expectedStudent);

    Student result = mockFinder.search();

    assertThat(result).extracting(Student::getName).isEqualTo("Sasha");
    assertThat(result).extracting(Student::getAge).isEqualTo(45);
  }

  @Test
  public void test_method_params_any() {
    Mockito.when(mockApiService.isItemFound(any())).thenReturn(true);

    assertThat(mockApiService.isItemFound(null)).isTrue();
  }

  @Test
  public void test_method_params_eq() {
    Mockito.when(mockApiService.isItemFound(Mockito.argThat(arg -> arg != null && arg.toString().length() < 3))).thenReturn(true);
    assertThat(mockApiService.isItemFound(test -> false)).isTrue();
  }
}
