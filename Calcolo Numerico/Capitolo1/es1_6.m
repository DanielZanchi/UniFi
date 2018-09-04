clc;
format long 
f1 = @(x) (x + 3/x)/2; % Prima successione
f2 = @(x0, x1) (3 + x0*x1)/(x0 + x1); % Seconda successione
x = sqrt(3); % Valore da convergere

xk = 3; %Innesco per la successione f1

k = 1;
e = 10^(-12);
diff = abs(xk - sqrt(3));

disp('Prima successione: ');
fprintf('k = 0\t\tx(0) = %.13f\t err = %.15f', xk, diff);

while ((e <= diff) && xk ~= sqrt(3))
   xk = f1(xk);
   diff = abs(xk - sqrt(3));
   fprintf('\nk = %d\t\tx(%d) = %.13f\t err = %.15f', k, k, xk, (diff));
   k = k + 1;
end

fprintf("\n\nSeconda successione:\n");
x0 = 3; %innesco per succeccione f2
x1 = 2; %innesco per successione f2
xk = 2; %innesco che cambiera nel ciclo
k = 2;
diff = x0 - sqrt(3);
fprintf('k = 0\t\tx(0) = %.13f \t err = %.15f', x0, diff);
diff = abs(x1 - sqrt(3));
fprintf('\nk = 1\t\tx(1) = %.13f \t err = %.15f', x1, diff);
while ((e <= diff) && (x0 ~= sqrt(3)))
   xk = f2(x0, x1);
   diff = abs(xk - sqrt(3));
   fprintf('\nk = %d\t\tx(%d) = %.13f\t err = %.15f', k, k, xk, (diff));
   x0 = x1;
   x1 = xk;
   k = k + 1; 
end



