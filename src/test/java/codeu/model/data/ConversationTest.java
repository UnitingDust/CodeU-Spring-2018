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

import java.time.Instant;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Test;

public class ConversationTest {

  @Test
  public void testCreate() {
    UUID id = UUID.randomUUID();
    UUID owner = UUID.randomUUID();
    String title = "Test_Title";
    Instant creation = Instant.now();

    Conversation conversation = new Conversation(id, owner, title, creation);

    Assert.assertEquals(id, conversation.getId());
    Assert.assertEquals(owner, conversation.getOwnerId());
    Assert.assertEquals(title, conversation.getTitle());
    Assert.assertEquals(creation, conversation.getCreationTime());
    Assert.assertEquals("public", conversation.getType()); 
  }

  @Test
  public void testCreatePrivate() {
    UUID id = UUID.randomUUID();
    UUID owner = UUID.randomUUID();
    String title = "Test_Title";
    Instant creation = Instant.now();
    String type = "private"; 

    Conversation conversation = new Conversation(id, owner, title, creation, type);

    Assert.assertEquals(id, conversation.getId());
    Assert.assertEquals(owner, conversation.getOwnerId());
    Assert.assertEquals(title, conversation.getTitle());
    Assert.assertEquals(creation, conversation.getCreationTime());
    Assert.assertEquals(type, conversation.getType()); 
    Assert.assertEquals(true, conversation.isAdmin(owner));
  }

  @Test
  public void testAddUsers() {
    UUID id = UUID.randomUUID();
    UUID owner = UUID.randomUUID();
    String title = "Test_Title";
    Instant creation = Instant.now();
    String type = "private"; 
    UUID idOne = UUID.randomUUID(); 
    UUID idTwo = UUID.randomUUID(); 
    UUID idAdmin = UUID.randomUUID(); 

    Conversation conversation = new Conversation(id, owner, title, creation, type);
    conversation.addUser(idOne); 
    conversation.addAdmin(idAdmin); 

    Assert.assertEquals(id, conversation.getId());
    Assert.assertEquals(owner, conversation.getOwnerId());
    Assert.assertEquals(title, conversation.getTitle());
    Assert.assertEquals(creation, conversation.getCreationTime());
    Assert.assertEquals(type, conversation.getType()); 

    Assert.assertEquals(true, conversation.isAdmin(owner)); 
    Assert.assertEquals(true, conversation.isAllowedUser(idOne));
    Assert.assertEquals(false, conversation.isAllowedUser(idTwo));  
    Assert.assertEquals(true, conversation.isAdmin(idAdmin));
    Assert.assertEquals(false, conversation.isAdmin(idOne));
  }
}
