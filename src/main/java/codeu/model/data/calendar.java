// Copyright 2017 Google Inc.

//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package codeu.model.data;

//import java.time.Instant;


/** Class representing a calendar. Dates are sent by a User in a Group Conversation. */
public class calendar{

  private String month;
  private String day;
  private String year;
  private String hour;
  private String minute;


  /**
   * Constructs a new event date.
   *
   * @param month of event
   * @param day of the event
   * @param year of the event
   * @param hour of the event
   * @param minute of the event
   */
  public calendar(String month, String day, String year, String hour, String minute) {
    this.month = month;
    this.day = day;
    this.year = year;
    this.hour = hour;
    this.minute = minute;
  }

  /** Returns the month of the event. */
  public String getMonth() {
    return this.month;
  }

  /** Returns the day of the event */
  public String getDay() {
    return this.day;
  }

  /** Returns the year of the event */
  public String getYear() {
    return this.year;
  }

   /** Returns the hour of the event */
  public String getHour() {
    return this.hour;
  }

   /** Returns the minute of the event */
  public String getMinute() {
    return this.minute;
  }

  public void setDate(String month, String day, String year, String hour, String minute){
    this.month = month;
    this.day = day;
    this.year = year;
    //this.hour = hour;
    //this.minute = minute;

  }


}